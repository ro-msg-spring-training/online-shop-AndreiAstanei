package ro.msg.learning.shop.orderStrategies;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import ro.msg.learning.shop.dtos.LocationOrderDTOs.LocationDirectionsMatrixAPI;
import ro.msg.learning.shop.dtos.LocationOrderDTOs.SimplifiedLocationIdAndAddress;
import ro.msg.learning.shop.dtos.LocationOrderDTOs.SimplifiedLocationIdAndDistance;
import ro.msg.learning.shop.dtos.orderDto.OrderDTOInput;
import ro.msg.learning.shop.dtos.orderDto.OrderDTOOutput;
import ro.msg.learning.shop.dtos.orderDto.ProcessedOrderProduct;
import ro.msg.learning.shop.dtos.orderDto.SimpleProductQuantity;
import ro.msg.learning.shop.entities.Location;
import ro.msg.learning.shop.exceptions.OrderPlacingException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class ProximityToLocationStrategy extends OrderGenerator implements OrderPlacingStrategiesInterface {
    private String apiKey;

    public ProximityToLocationStrategy(String apiKey) {
        this.apiKey = apiKey;
    }

    public List<SimplifiedLocationIdAndDistance> searchForLocations(String apiKey) throws OrderPlacingException, JSONException, IOException {
        List<SimplifiedLocationIdAndDistance> locationsList;

        // All available locations with their corresponding addresses
        List<SimplifiedLocationIdAndAddress> availableLocationsWorldwide = locationRepository.findAll().stream().map(currentLocation ->
                SimplifiedLocationIdAndAddress.builder().id(currentLocation.getId()).location(locationMapper.mapLocationToDirectionsMatrixLocation(currentLocation)).build()).collect(Collectors.toList());

        // Getting the Directions Matrix API response for the available locations
        // Creating the request
        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "http://www.mapquestapi.com";
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject searchObject = new JSONObject();
        JSONObject searchOptions = new JSONObject();
        JSONArray locations = new JSONArray();

        // Adding the locations to the request body, along side the delivery address(first one in the list)
        locations.put(LocationDirectionsMatrixAPI.builder().street("Bulevardul Cetatii 93").city("Timisoara").country("RO").build());
        availableLocationsWorldwide.forEach(location -> locations.put(new LocationDirectionsMatrixAPI(location.getLocation())));

        searchObject.put("locations", locations);
        searchOptions.put("manyToOne", true);
        searchOptions.put("unit", "k");
        searchObject.put("options", searchOptions);

        // Sending the request and parsing the response
        HttpEntity<String> request = new HttpEntity<>(searchObject.toString(), headers);

        String response = restTemplate.postForObject(baseUrl + "/directions/v2/routematrix?key=" + apiKey, request, String.class);

        JsonNode node = null;
        if (response != null) {
            node = objectMapper.readTree(response);

            ObjectReader reader = objectMapper.readerFor(new TypeReference<List<String>>() {
            });
            List<String> apiCallStringResponse = reader.readValue(node.get("distance"));

            List<Double> distanceDataFromAPICall = (apiCallStringResponse.subList(1, apiCallStringResponse.size())).stream().map(Double::parseDouble).collect(Collectors.toList());

            locationsList = locationMapper.mapLocationsIdsAndDistanceData(
                    locationRepository.getLocationsIds(),
                    distanceDataFromAPICall
            );

            if (locationsList.size() > 0) {
                // Sorting the list, so that the closest locations are at the beginning
                return locationsList.stream().sorted(Comparator.comparing(SimplifiedLocationIdAndDistance::getDistance)).collect(Collectors.toList());
            } else {
                throw new OrderPlacingException("Could not find a list of locations! Please check the delivery address and try again.");
            }
        } else {
            throw new OrderPlacingException("Could not establish distance parameters between storing locations!");
        }
    }

    @Override
    public OrderDTOOutput generateOrder(OrderDTOInput inputData) throws OrderPlacingException {
        OrderDTOOutput finalResult = null;
        List<SimplifiedLocationIdAndDistance> locationsWithDistanceData;

        try {
            locationsWithDistanceData = searchForLocations(this.apiKey);

            // Creating the order... we have 2 lists
            List<SimpleProductQuantity> targetShoppingCart = inputData.getProductsList();
            List<SimpleProductQuantity> currentShoppingCart = new ArrayList<>();
            List<ProcessedOrderProduct> stocksToBeUpdated = new ArrayList<>();

            // for each location, see what products you can take
            locationsWithDistanceData.forEach(location -> {
                Optional<Location> receivedLocationFromDB = locationRepository.findById(location.getId());
                if (receivedLocationFromDB.isPresent()) {
                    Location currentLocation = receivedLocationFromDB.get();

                    // for each stock
                    currentLocation.getStocks().forEach(stock -> {
                        // and for each product in target cart
                        targetShoppingCart.forEach(targetProduct -> {
                            // IF CC != TC
                            if (!currentShoppingCart.containsAll(targetShoppingCart)) {
                                // check and update(if necessary)
                                if (stock.getProduct().getId().equals(targetProduct.getProductId())) {
                                    // Additional check, because on the first run CC will be empty, so there is no point in searching there
                                    if (currentShoppingCart.size() == 0) {
                                        // if the CC is empty, we do the same thing as if in the second case from further below

                                        // we do not already have the product in the CC, so we add it(***)
                                        int requiredQuantityFromStock = targetProduct.getProductQuantity();
                                        int quantityUpdateBasedOnExistingAndTargetValues = requiredQuantityFromStock > stock.getQuantity() ? stock.getQuantity() : requiredQuantityFromStock;

                                        // Additional check on the updatedQuantity before applying changes
                                        if (quantityUpdateBasedOnExistingAndTargetValues > 0) {
                                            // Updating CC and STBU
                                            currentShoppingCart.add(SimpleProductQuantity.builder().productId(targetProduct.getProductId()).productQuantity(quantityUpdateBasedOnExistingAndTargetValues).build());
                                            stocksToBeUpdated.add(ProcessedOrderProduct.builder()
                                                    .locationId(currentLocation.getId())
                                                    .productId(targetProduct.getProductId())
                                                    .productQuantity(quantityUpdateBasedOnExistingAndTargetValues)
                                                    .build()
                                            );
                                        }
                                    } else {
                                        // we have the product on stock, but we need to verify that we don't already have it in the CC
                                        if (doesProductExistInCC(currentShoppingCart, targetShoppingCart, targetProduct.getProductId())) {
                                            // we do already have the product in the CC, so we check for quantities(***)
                                            Integer currentCartProductQuantity = currentShoppingCart.get(getProductIndexFromShoppingCartList(currentShoppingCart, targetProduct.getProductId())).getProductQuantity();

                                            // if we don't have the required quantity
                                            if (!currentCartProductQuantity.equals(targetProduct.getProductQuantity())) {
                                                // find the quantity needed to fulfill order request for this product
                                                int requiredQuantityFromStock = targetProduct.getProductQuantity() - currentCartProductQuantity;

                                                // We want to see how much we have to take from the quantity on the stock
                                                // subtract it from the stock, then update the CC and the STBU
                                                int quantityUpdateBasedOnExistingAndTargetValues = requiredQuantityFromStock > stock.getQuantity() ? stock.getQuantity() : requiredQuantityFromStock;

                                                // Additional check on the updatedQuantity before applying changes
                                                if (quantityUpdateBasedOnExistingAndTargetValues > 0) {
                                                    // Updating CC and STBU
                                                    currentShoppingCart.get(getProductIndexFromShoppingCartList(currentShoppingCart, targetProduct.getProductId())).setProductQuantity(currentCartProductQuantity + quantityUpdateBasedOnExistingAndTargetValues);
                                                    stocksToBeUpdated.add(ProcessedOrderProduct.builder()
                                                            .locationId(currentLocation.getId())
                                                            .productId(targetProduct.getProductId())
                                                            .productQuantity(quantityUpdateBasedOnExistingAndTargetValues)
                                                            .build()
                                                    );
                                                }
                                            }
                                        } else {
                                            // we do not already have the product in the CC, so we add it(***)
                                            int requiredQuantityFromStock = targetProduct.getProductQuantity();
                                            int quantityUpdateBasedOnExistingAndTargetValues = requiredQuantityFromStock > stock.getQuantity() ? stock.getQuantity() : requiredQuantityFromStock;

                                            // Additional check on the updatedQuantity before applying changes
                                            if (quantityUpdateBasedOnExistingAndTargetValues > 0) {
                                                // Updating CC and STBU
                                                currentShoppingCart.add(SimpleProductQuantity.builder().productId(targetProduct.getProductId()).productQuantity(quantityUpdateBasedOnExistingAndTargetValues).build());
                                                stocksToBeUpdated.add(ProcessedOrderProduct.builder()
                                                        .locationId(currentLocation.getId())
                                                        .productId(targetProduct.getProductId())
                                                        .productQuantity(quantityUpdateBasedOnExistingAndTargetValues)
                                                        .build()
                                                );
                                            }
                                        }
                                    }
                                }
                            }
                        });
                    });
                } else {
                    log.error("Proximity Search Error - could not find given location in database!");
                }
            });

            if (currentShoppingCart.containsAll(targetShoppingCart)) {
                finalResult = orderMapper.mapOrderToOrderDTOOutput(placeOrder(stocksToBeUpdated, inputData));
            } else {
                throw new OrderPlacingException("Could not find a suitable list of locations for the given order!");
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        if (finalResult != null) {
            return finalResult;
        } else {
            throw new OrderPlacingException("Order not created! There was a problem while creating order with the closest locations setting.");
        }
    }

    // We have the TC here only because we must make sure the ID of the product is ok
    private Boolean doesProductExistInCC(List<SimpleProductQuantity> cc, List<SimpleProductQuantity> tc, Integer productId) {
        Integer ccIndex = getProductIndexFromShoppingCartList(cc, productId);
        Integer tcIndex = getProductIndexFromShoppingCartList(tc, productId);

        SimpleProductQuantity ccProduct = cc.get(ccIndex);
        SimpleProductQuantity tcProduct = tc.get(tcIndex);

        return ccProduct.getProductId().equals(tcProduct.getProductId());
    }

    private Integer getProductIndexFromShoppingCartList(List<SimpleProductQuantity> cart, Integer productId) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getProductId().equals(productId)) {
                return i;
            }
        }

        return 0;
    }
}
