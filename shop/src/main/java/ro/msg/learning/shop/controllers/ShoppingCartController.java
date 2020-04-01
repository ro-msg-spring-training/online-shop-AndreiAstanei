package ro.msg.learning.shop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.services.shoppingCartService.ShoppingCartServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/shoppingCart")
public class ShoppingCartController {
    private final ShoppingCartServiceImpl shoppingCartService;


}
