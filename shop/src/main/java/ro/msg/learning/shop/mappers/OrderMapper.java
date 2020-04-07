package ro.msg.learning.shop.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dtos.orderDto.OrderDTOOutput;
import ro.msg.learning.shop.entities.Order;

import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class OrderMapper {
    private final OrderDetailMapper orderDetailMapper;

    public OrderDTOOutput mapOrderToOrderDTOOutput(Order order) {
        DateTimeFormatter outputDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return OrderDTOOutput.builder()
                .id(order.getId())
                .customerId(order.getCustomer().getId())
                .createdAt(order.getCreatedAt().plusMinutes(order.getOrder_timestamp()).format(outputDateFormatter))
                .country(order.getEmbeddableAddress().getCountry())
                .county(order.getEmbeddableAddress().getCounty())
                .city(order.getEmbeddableAddress().getCity())
                .streetAddress(order.getEmbeddableAddress().getStreetAddress())
                .productsList(orderDetailMapper.mapOrderDetailToSimpleProductQuantityList(order.getOrderDetails()))
                .build();
    }
}
