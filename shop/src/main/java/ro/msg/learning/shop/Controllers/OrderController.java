package ro.msg.learning.shop.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.msg.learning.shop.DTOs.orderDto.OrderDTOInput;
import ro.msg.learning.shop.DTOs.orderDto.OrderDTOOutput;
import ro.msg.learning.shop.Services.OrderServiceImpl;
import ro.msg.learning.shop.exceptions.OrderPlacingException;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/orders")
public class OrderController {
    private final OrderServiceImpl orderService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<OrderDTOOutput> getOrders() {
        return orderService.getOrders();
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public OrderDTOOutput createOrder(@RequestBody OrderDTOInput orderDTOInputData) {
        try {
            return orderService.generateOrder(orderDTOInputData);
        } catch (OrderPlacingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
