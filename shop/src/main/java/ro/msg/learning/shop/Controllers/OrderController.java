package ro.msg.learning.shop.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.msg.learning.shop.DTOs.orderDto.OrderDTOInput;
import ro.msg.learning.shop.DTOs.orderDto.OrderDTOOutput;
import ro.msg.learning.shop.Services.OrderServiceImpl;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/orders")
public class OrderController {
    private final OrderServiceImpl orderService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<OrderDTOOutput> getOrders() {
        return orderService.getOrders();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public OrderDTOOutput createOrder(@RequestBody OrderDTOInput orderDTOInputData) {
        return orderService.generateOrder(orderDTOInputData);
    }
}
