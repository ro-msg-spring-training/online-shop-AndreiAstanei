package ro.msg.learning.shop.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import ro.msg.learning.shop.DTOs.LocationOrderDTOs.LocationDirectionsMatrixAPI;
import ro.msg.learning.shop.DTOs.LocationOrderDTOs.SimplifiedLocationIdAndAddress;
import ro.msg.learning.shop.DTOs.LocationOrderDTOs.SimplifiedLocationIdAndDistance;
import ro.msg.learning.shop.DTOs.orderDto.OrderDTOInput;
import ro.msg.learning.shop.DTOs.orderDto.OrderDTOOutput;
import ro.msg.learning.shop.DTOs.orderDto.ProcessedOrderProduct;
import ro.msg.learning.shop.DTOs.orderDto.SimpleProductQuantity;
import ro.msg.learning.shop.Entities.*;
import ro.msg.learning.shop.Repositories.*;
import ro.msg.learning.shop.exceptions.OrderPlacingException;
import ro.msg.learning.shop.mappers.LocationMapper;
import ro.msg.learning.shop.mappers.OrderMapper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class OrderStrategyConfiguration {
    private final StockRepository stockRepository;
    private final CustomerRepository customerRepository;
    private final LocationRepository locationRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
    private final LocationMapper locationMapper;
    private final ObjectMapper objectMapper;

    @Value(value = "${order_selection_strategy}")
    private OrderStrategies selectedStrategy;

    @Value(value = "${directions_route_matrix_api_key}")
    private String routeMatrixApiKey;

    public OrderDTOOutput generateOrderByStrategy(OrderDTOInput orderData) throws OrderPlacingException, JSONException, IOException {
        switch(selectedStrategy) {
            case MOST_ABUNDANT:
                return mostAbundantStrategy(orderData);

            case SINGLE_LOCATION:
                return singleLocationStrategy(orderData);

            case PROXIMITY_TO_LOCATION:
                return proximityToLocationStrategy(orderData);

            default:
                return singleLocationStrategy(orderData);
        }
    }

    private OrderDTOOutput singleLocationStrategy(OrderDTOInput inputData) throws OrderPlacingException {
        /* go through every location
         search for required quantity till we reach the end of desired products
         if we find at least 1 location that doesn't have enough products in stock, we go to the next location
         if we reached the end of desires products and are still at the same location, we proceed with making the changes in the database
        */
        List<Integer> locationIds = stockRepository.getLocationsList();
        boolean currentLocationIsSuitedForFulfillingOrder = true;
        Integer suitableLocationId = 0;

        List<SimpleProductQuantity> currentProdutsOnStock;
        List<SimpleProductQuantity> orderedProducts = new ArrayList<>();

        Order responseOrder = new Order();
        OrderDTOOutput serverResponseToFrontend = new OrderDTOOutput();

        try {
            // Getting stocks for a certain location
            for (Integer locationId : locationIds) {
                currentLocationIsSuitedForFulfillingOrder = true;
                List<Stock> stocks = stockRepository.findStocksByLocation_Id(locationId);
                currentProdutsOnStock = stocks.stream().map(stock -> new SimpleProductQuantity(stock.getProduct().getId(), stock.getQuantity())).sorted(Comparator.comparing(SimpleProductQuantity::getProductId)).collect(Collectors.toList());
                orderedProducts = inputData.getProductsList().stream().map(product -> new SimpleProductQuantity(product.getProductId(), product.getProductQuantity())).sorted(Comparator.comparing(SimpleProductQuantity::getProductId)).collect(Collectors.toList());

                // If the list of products is present in the current stock
                if (currentProdutsOnStock.containsAll(orderedProducts)) {
                    suitableLocationId = locationId;
                    break;
                } else {
                    currentLocationIsSuitedForFulfillingOrder = false;
                }
            }

            final Integer finalLocationId = suitableLocationId;
            List<ProcessedOrderProduct> readyToBeProccessedProducts = inputData.getProductsList().stream().map(product -> new ProcessedOrderProduct(finalLocationId, product.getProductId(), product.getProductQuantity())).collect(Collectors.toList());

            if (currentLocationIsSuitedForFulfillingOrder) {
                // Creating the order
                responseOrder = placeOrder(readyToBeProccessedProducts, inputData);

                serverResponseToFrontend = orderMapper.mapOrderToOrderDTOOutput(responseOrder);
            } else {
                throw new OrderPlacingException("Nu s-a gasit o locatie corespunzatoare!");
            }
        } catch (OrderPlacingException ex) {
            System.out.println(ex.getMessage());
            throw new OrderPlacingException("Nu s-a gasit o locatie corespunzatoare!");
        }

        return serverResponseToFrontend;
    }

    private OrderDTOOutput mostAbundantStrategy(OrderDTOInput inputData) {
        List<SimpleProductQuantity> orderedProducts;
        List<ProcessedOrderProduct> locationIdsList = new ArrayList<>();

        OrderDTOOutput serverResponseToFrontEnd = new OrderDTOOutput();

        try {
            orderedProducts = inputData.getProductsList().stream().map(product -> new SimpleProductQuantity(product.getProductId(), product.getProductQuantity())).sorted(Comparator.comparing(SimpleProductQuantity::getProductId)).collect(Collectors.toList());

            // Trying to find a suitable location for each ordered product, and then saving that location in a list
            orderedProducts.forEach(item -> {
                Stock suitableStock = stockRepository.getStockForMaxProductQuantity(item.getProductId(), item.getProductQuantity());

                if (suitableStock != null) {
                    locationIdsList.add(new ProcessedOrderProduct(suitableStock.getLocation().getId(), item.getProductId(), item.getProductQuantity()));
                }
            });

            // Once we have all the products in our list, we go ahead and place the order and update the stocks
            if (checkFoundLocationsValidityByProductPresence(locationIdsList, orderedProducts)) {
                serverResponseToFrontEnd = orderMapper.mapOrderToOrderDTOOutput(placeOrder(locationIdsList, inputData));
            } else {
                throw new OrderPlacingException("Nu s-a gasit o lista de locatii corespunzatoare!");
            }
        } catch (OrderPlacingException ex) {
            System.out.println(ex.getMessage());
        }

        return serverResponseToFrontEnd;
    }

    private OrderDTOOutput proximityToLocationStrategy(OrderDTOInput inputData) throws JSONException, IOException, OrderPlacingException {
        // ------------------------- Implementation
        OrderDTOOutput finalResult = null;

        // All available locations with their corresponding addresses
        List<SimplifiedLocationIdAndAddress> availableLocationsWorldwide = locationRepository.findAll().stream().map(currentLocation ->
        {
            Location currentWarehouseLocation = Location.builder().id(currentLocation.getId()).addressCountry(currentLocation.getAddressCountry()).addressCounty(currentLocation.getAddressCounty()).addressCity(currentLocation.getAddressCity()).addressStreetAddress(currentLocation.getAddressStreetAddress()).build();
            return SimplifiedLocationIdAndAddress.builder().id(currentLocation.getId()).location(locationMapper.mapLocationToDirectionsMatrixLocation(currentWarehouseLocation)).build();
        }).collect(Collectors.toList());

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
        availableLocationsWorldwide.forEach(location -> {
            locations.put(new LocationDirectionsMatrixAPI(location.getLocation()));
        });

        searchObject.put("locations", locations);
        searchOptions.put("manyToOne", true);
        searchOptions.put("unit", "k");
        searchObject.put("options", searchOptions);

        // Sending the request and parsing the response
        HttpEntity<String> request = new HttpEntity<>(searchObject.toString(), headers);

        String response = restTemplate.postForObject(baseUrl + "/directions/v2/routematrix?key=" + routeMatrixApiKey, request, String.class);

        JsonNode node = null;
        if (response != null) {
            node = objectMapper.readTree(response);

            ObjectReader reader = objectMapper.readerFor(new TypeReference<List<String>>() {
            });
            List<Double> distanceDataFromAPICall = reader.readValue(node.get("distance"));

            List<SimplifiedLocationIdAndDistance> locationsWithDistanceData = locationMapper.mapLocationsIdsAndDistanceData(
                    locationRepository.findAll().stream().map(Location::getId).collect(Collectors.toList()),
                    distanceDataFromAPICall.subList(1, distanceDataFromAPICall.size())
            );

            System.out.println(locationsWithDistanceData);


            return finalResult;
        } else {
            throw new OrderPlacingException("Could not establish distance parameters between storing locations!");
        }
    }

    private boolean checkFoundLocationsValidityByProductPresence(List<ProcessedOrderProduct> locationIdsList, List<SimpleProductQuantity> orderedProducts) {
        boolean result = false;

        List<Integer> orderedProductsIdList = orderedProducts.stream().map(SimpleProductQuantity::getProductId).collect(Collectors.toList());
        List<Integer> foundLocationsProductsIdList = locationIdsList.stream().map(ProcessedOrderProduct::getProductId).collect(Collectors.toList());

        if (orderedProductsIdList.containsAll(foundLocationsProductsIdList)) {
            result = true;
        }

        return result;
    }

    private Order placeOrder(List<ProcessedOrderProduct> readyToBeProccessedProducts, OrderDTOInput inputData) {
        Order responseOrder;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Integer offset = ZonedDateTime.now().getOffset().getTotalSeconds() / 60; // Local timezone offset for server(UTC)

        // *** Adding the customer
        Customer orderCustomer = null;
        if (customerRepository.findById(inputData.getCustomerId()).isPresent()) {
            orderCustomer = customerRepository.findById(inputData.getCustomerId()).get();
        }

        // *** Setting the locations for the order
        Set<Location> orderLocation = new HashSet<>();
        readyToBeProccessedProducts.forEach(item -> {
            if (locationRepository.findById(item.getLocationId()).isPresent()) {
                orderLocation.add(locationRepository.findById(item.getLocationId()).get());
            }
        });

        // *** Creating the order
        Order finalUserOrder = Order.builder()
                .createdAt(LocalDateTime.parse(inputData.getCreatedAt(), formatter))
                .order_timestamp(offset + inputData.getOrder_timestamp())   //calculating the offset for the client based on the client's timezone and the server's timezone
                .customer(orderCustomer)
                .shippedFrom(orderLocation)
                .addressCity(inputData.getAddressCity())
                .addressCountry(inputData.getAddressCountry())
                .addressCounty(inputData.getAddressCounty())
                .addressStreetAddress(inputData.getAddressStreetAddress())
                .build();

        // Saving the new Order in the database
        responseOrder = orderRepository.save(finalUserOrder);

        // *** Updating the stocks in the database
        readyToBeProccessedProducts.forEach(item -> {
            Stock stock = stockRepository.findByLocation_IdAndProduct_Id(item.getLocationId(), item.getProductId());
            stock.setQuantity(stock.getQuantity() - item.getProductQuantity());
            stockRepository.save(stock);
        });

        // *** Creating the required entries in the order history table
        Order finalResponseOrder = responseOrder;
        readyToBeProccessedProducts.forEach(item -> {
            Product tempProduct = new Product();
            if (productRepository.findById(item.getProductId()).isPresent()) {
                tempProduct = productRepository.findById(item.getProductId()).get();
            }

            OrderDetail orderDetail = OrderDetail.builder()
                    .quantity(item.getProductQuantity())
                    .order(finalResponseOrder)
                    .product(tempProduct)
                    .build();

            orderDetailRepository.save(orderDetail);
        });

        // Updating the order with the newly created OrderDetails(more like history actually)
        List<OrderDetail> orderDetails = orderDetailRepository.findAllByOrder_Id(finalResponseOrder.getId());
        finalResponseOrder.setOrderDetails(orderDetails);

        return orderRepository.save(finalResponseOrder);
    }
}