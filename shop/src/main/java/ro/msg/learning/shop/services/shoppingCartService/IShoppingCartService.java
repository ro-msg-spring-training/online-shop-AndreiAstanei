package ro.msg.learning.shop.services.shoppingCartService;

import org.springframework.security.core.userdetails.UserDetails;
import ro.msg.learning.shop.dtos.productDto.ProductDTO;
import ro.msg.learning.shop.entities.shoppingCartEntities.ShoppingCart;

import java.util.List;

public interface IShoppingCartService {
    List<ProductDTO> getAllProducts();

    UserDetails getAuthenticatedUserDetails();

    boolean addProductToCart(Integer productId);

    ShoppingCart getCustomerShoppingCart();

    void decreaseProductQuantity(Integer productId);

    void increaseProductQuantity(Integer productId);

    void deleteProductFromCart(Integer productId);
}
