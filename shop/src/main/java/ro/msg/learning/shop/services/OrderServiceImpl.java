package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.dtos.orderDto.OrderDTOInput;
import ro.msg.learning.shop.dtos.orderDto.OrderDTOOutput;
import ro.msg.learning.shop.exceptions.OrderPlacingException;
import ro.msg.learning.shop.mappers.OrderMapper;
import ro.msg.learning.shop.orderStrategies.OrderPlacingStrategiesInterface;
import ro.msg.learning.shop.repositories.jpaBasedRepositories.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderPlacingStrategiesInterface orderStrategy;

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
        return orderStrategy.generateOrder(orderDTOInputData);
    }
}
