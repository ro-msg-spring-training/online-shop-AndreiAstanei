package ro.msg.learning.shop.orderStrategies;

import ro.msg.learning.shop.dtos.orderDto.OrderDTOInput;
import ro.msg.learning.shop.dtos.orderDto.OrderDTOOutput;
import ro.msg.learning.shop.exceptions.OrderPlacingException;

public interface OrderPlacingStrategiesInterface {
    OrderDTOOutput generateOrder(OrderDTOInput inputData) throws OrderPlacingException;
}
