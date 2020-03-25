package ro.msg.learning.shop.orderStrategies;

import lombok.RequiredArgsConstructor;
import ro.msg.learning.shop.dtos.orderDto.OrderDTOInput;
import ro.msg.learning.shop.dtos.orderDto.OrderDTOOutput;
import ro.msg.learning.shop.dtos.orderDto.ProcessedOrderProduct;
import ro.msg.learning.shop.dtos.orderDto.SimpleProductQuantity;
import ro.msg.learning.shop.entities.Stock;
import ro.msg.learning.shop.exceptions.OrderPlacingException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MostAbundantStrategy extends OrderGenerator implements OrderPlacingStrategiesInterface {
    @Override
    public OrderDTOOutput generateOrder(OrderDTOInput inputData) throws OrderPlacingException {
        List<SimpleProductQuantity> orderedProducts;
        List<ProcessedOrderProduct> locationIdsList = new ArrayList<>();

        OrderDTOOutput serverResponseToFrontEnd = new OrderDTOOutput();

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
            throw new OrderPlacingException("Could not find a suitable list of locations for the given order!");
        }

        return serverResponseToFrontEnd;
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
}
