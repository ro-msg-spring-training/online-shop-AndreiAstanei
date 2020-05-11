package ro.msg.learning.shop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ro.msg.learning.shop.dtos.shoppingCartDTO.DeliveryAddress;
import ro.msg.learning.shop.entities.shoppingCartEntities.ShoppingCart;
import ro.msg.learning.shop.services.shoppingCartService.ShoppingCartServiceImpl;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartServiceImpl shoppingCartService;

    @GetMapping(value = "/display-products")
    public String displayProductsPage(Model model) {
        model.addAttribute("products", shoppingCartService.getAllProducts());
        return "display-products";
    }

    @PostMapping(value = "/display-products")
    public String displayProducts(Model model) {
        UserDetails userDetails = shoppingCartService.getAuthenticatedUserDetails();
        if (userDetails != null) {
            System.out.println(userDetails.getUsername());
        }

        model.addAttribute("products", shoppingCartService.getAllProducts());

        return "display-products";
    }

    @GetMapping(value = "/shopping-cart")
    public String displayShoppingCartGet(Model model) {
        ShoppingCart cart = shoppingCartService.getCustomerShoppingCart();

        model.addAttribute("cart", cart);

        return "shopping-cart";
    }

    @PostMapping(value = "/shopping-cart")
    public String displayShoppingCartPost() {
        return "shopping-cart";
    }

    @GetMapping(value = "/add-products-to-cart/")
    public String addProductToCart(@RequestParam(name = "id") Integer id, Model model) {
        boolean operationStatus = shoppingCartService.addProductToCart(id);

        model.addAttribute("operationStatus", operationStatus);
        return "added-product-to-cart";
    }

    @GetMapping(value = "/cart-products-delete-all")
    public RedirectView deleteProductFromCart(@RequestParam(name = "id") Integer id, RedirectAttributes attributes) {
        shoppingCartService.deleteProductFromCart(id);

        ShoppingCart cart = shoppingCartService.getCustomerShoppingCart();

        attributes.addAttribute("cart", cart);
        return new RedirectView("../shopping-cart");
    }

    @GetMapping(value = "/cart-products-decrease")
    public RedirectView decreareProductQuantity(@RequestParam(name = "id") Integer id, RedirectAttributes attributes) {
        shoppingCartService.decreaseProductQuantity(id);

        ShoppingCart cart = shoppingCartService.getCustomerShoppingCart();

        attributes.addAttribute("cart", cart);
        return new RedirectView("../shopping-cart");
    }

    @GetMapping(value = "/cart-products-increase")
    public RedirectView increaseProductQuantity(@RequestParam(name = "id") Integer id, RedirectAttributes attributes) {
        shoppingCartService.increaseProductQuantity(id);

        ShoppingCart cart = shoppingCartService.getCustomerShoppingCart();

        attributes.addAttribute("cart", cart);
        return new RedirectView("../shopping-cart");
    }

    @GetMapping(value = "/place-order")
    public String placeOrderGet() {
        return "place-order";
    }

    @PostMapping(value = "/place-order")
    public String placeOrderPost() {
        return "place-order";
    }

    @GetMapping(value = "/place-order-address")
    public String placeOrderAddressGet(Model model, DeliveryAddress deliveryAddress) {

        model.addAttribute("deliveryAddress", deliveryAddress);
        return "place-order-address";
    }

    @PostMapping(value = "/place-order-address")
    public String placeOrderAddressPost(Model model, DeliveryAddress deliveryAddress) {
        boolean operationStatus = shoppingCartService.placeOrder(deliveryAddress);

        model.addAttribute("operationStatus", operationStatus);
        model.addAttribute("deliveryAddress", deliveryAddress);
        return "place-order";
    }
}
