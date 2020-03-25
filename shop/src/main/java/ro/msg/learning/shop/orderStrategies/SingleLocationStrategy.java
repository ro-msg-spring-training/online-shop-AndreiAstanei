package ro.msg.learning.shop.orderStrategies;

import lombok.RequiredArgsConstructor;
import ro.msg.learning.shop.dtos.orderDto.OrderDTOInput;
import ro.msg.learning.shop.dtos.orderDto.OrderDTOOutput;
import ro.msg.learning.shop.dtos.orderDto.ProcessedOrderProduct;
import ro.msg.learning.shop.dtos.orderDto.SimpleProductQuantity;
import ro.msg.learning.shop.entities.Order;
import ro.msg.learning.shop.exceptions.OrderPlacingException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SingleLocationStrategy extends OrderGenerator implements OrderPlacingStrategiesInterface {
    @Override
    public OrderDTOOutput generateOrder(OrderDTOInput inputData) throws OrderPlacingException {
       /* go through every location
         search for required quantity till we reach the end of desired products
         if we find at least 1 location that doesn't have enough products in stock, we go to the next location
         if we reached the end of desires products and are still at the same location, we proceed with making the changes in the database
        */
        List<Integer> locationIds = stockRepository.findLocationIdsForAllOrderedProducts(
                inputData.getProductsList().stream().map(SimpleProductQuantity::getProductId).collect(Collectors.toList()),
                inputData.getProductsList().size(),
                inputData.getProductsList().stream().min(Comparator.comparing(SimpleProductQuantity::getProductQuantity)).get().getProductQuantity()
        );

        boolean currentLocationIsSuitedForFulfillingOrder = true;
        Integer suitableLocationId = 0;

        List<SimpleProductQuantity> currentProdutsOnStock;
        List<SimpleProductQuantity> orderedProducts = new ArrayList<>();

        Order responseOrder = new Order();
        OrderDTOOutput serverResponseToFrontend = new OrderDTOOutput();

        // Getting stocks for a certain location
        for (Integer locationId : locationIds) {
            currentLocationIsSuitedForFulfillingOrder = true;
            currentProdutsOnStock = stockRepository.getSPQListFromCurrentLocationStocks(locationId);
            orderedProducts = inputData.getProductsList().stream().map(product -> new SimpleProductQuantity(product.getProductId(), product.getProductQuantity())).sorted(Comparator.comparing(SimpleProductQuantity::getProductId)).collect(Collectors.toList());

            // If the list of products is present in the current stock
            // -> *** Previously  currentProdutsOnStock.containsAll(orderedProducts)
            if (doesCurrentStocksContainAllOrderedProducts(currentProdutsOnStock, orderedProducts)) {
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
            throw new OrderPlacingException("Comanda nu a putut fi plasata! Nu s-a gasit o locatie corespunzatoare care sa detina toate produsele cerute!");
        }

        return serverResponseToFrontend;
    }

    private Boolean doesCurrentStocksContainAllOrderedProducts(List<SimpleProductQuantity> list1, List<SimpleProductQuantity> list2) {
        Collections.sort(list1);
        Collections.sort(list2);

        // First we make sure that both lists have the same size
        if (list1.size() >= list2.size()) {
            // Than we compare the id in the first place, and after that the product quantities
            for (int i = 0; i < list2.size(); i++) {
                if (!list1.get(i).getProductId().equals(list2.get(i).getProductId())) {
                    return false;
                }

                if (list1.get(i).getProductQuantity() < list2.get(i).getProductQuantity()) {
                    return false;
                }
            }
        } else {
            return false;
        }

        return true;
    }
}
