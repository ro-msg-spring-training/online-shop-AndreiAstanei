package ro.msg.learning.shop.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.DTOs.orderDto.OrderDTOInput;
import ro.msg.learning.shop.DTOs.orderDto.OrderDTOOutput;
import ro.msg.learning.shop.Repositories.OrderRepository;
import ro.msg.learning.shop.configuration.OrderStrategyConfiguration;
import ro.msg.learning.shop.exceptions.OrderPlacingException;
import ro.msg.learning.shop.mappers.OrderMapper;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {
    private final OrderStrategyConfiguration orderStrategyConfiguration;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;


    @Override
    public List<OrderDTOOutput> getOrders() {
        List<OrderDTOOutput> ordersList = new ArrayList<>();

        orderRepository.findAll().forEach(order -> {
            ordersList.add(orderMapper.mapOrderToOrderDTOOutput(order));
        });

        return ordersList;
    }

    @Override
    public OrderDTOOutput generateOrder(OrderDTOInput orderDTOInputData) throws OrderPlacingException {
        OrderDTOOutput orderDTOOutput = null;

        try {
            orderDTOOutput = orderStrategyConfiguration.generateOrderByStrategy(orderDTOInputData);
        } catch (OrderPlacingException e) {
            System.out.println(e.getMessage());
            throw new OrderPlacingException("No stock");
        }

        return orderDTOOutput;
    }
}
