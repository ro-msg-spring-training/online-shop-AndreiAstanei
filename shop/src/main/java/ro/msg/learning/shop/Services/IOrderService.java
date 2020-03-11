package ro.msg.learning.shop.Services;

import ro.msg.learning.shop.DTOs.orderDto.OrderDTOInput;
import ro.msg.learning.shop.DTOs.orderDto.OrderDTOOutput;
import ro.msg.learning.shop.exceptions.OrderPlacingException;

import java.util.List;

public interface IOrderService {
    List<OrderDTOOutput> getOrders();

    OrderDTOOutput generateOrder(OrderDTOInput orderDTOInputData) throws OrderPlacingException;
}
