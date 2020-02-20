package ro.msg.learning.shop.mappers;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.DTOs.orderDto.SimpleProductQuantity;
import ro.msg.learning.shop.Entities.OrderDetail;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderDetailMapper {
    public List<SimpleProductQuantity> mapOrderDetailToSimpleProductQuantityList(List<OrderDetail> orderDetail) {
        List<SimpleProductQuantity> simpleProductQuantityList = new ArrayList<>();

        orderDetail.forEach(item -> {
            simpleProductQuantityList.add(new SimpleProductQuantity(item.getProduct().getId(), item.getQuantity()));
        });

        return simpleProductQuantityList;
    }
}
