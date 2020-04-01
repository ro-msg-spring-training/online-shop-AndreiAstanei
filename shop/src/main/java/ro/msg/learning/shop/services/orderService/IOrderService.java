package ro.msg.learning.shop.services.orderService;

import ro.msg.learning.shop.dtos.orderDto.OrderDTOInput;
import ro.msg.learning.shop.dtos.orderDto.OrderDTOOutput;
import ro.msg.learning.shop.exceptions.OrderPlacingException;

import java.util.List;

public interface IOrderService {
    List<OrderDTOOutput> getOrders();

    OrderDTOOutput generateOrder(OrderDTOInput orderDTOInputData) throws OrderPlacingException;
}
