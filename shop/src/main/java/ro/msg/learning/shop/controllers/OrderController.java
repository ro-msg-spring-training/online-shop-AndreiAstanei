package ro.msg.learning.shop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dtos.orderDto.OrderDTOInput;
import ro.msg.learning.shop.dtos.orderDto.OrderDTOOutput;
import ro.msg.learning.shop.exceptions.OrderPlacingException;
import ro.msg.learning.shop.services.orderService.OrderServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/orders")
public class OrderController {
    private final OrderServiceImpl orderService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OrderDTOOutput> getOrders() {
        return orderService.getOrders();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderDTOOutput createOrder(@RequestBody OrderDTOInput orderDTOInputData) throws OrderPlacingException {
        return orderService.generateOrder(orderDTOInputData);
    }
}
