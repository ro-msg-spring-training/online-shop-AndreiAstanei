package ro.msg.learning.shop.services.shoppingCartService;

import ro.msg.learning.shop.dtos.orderDto.OrderDTOOutput;
import ro.msg.learning.shop.entities.mongoDbDocuments.ShoppingCart;

public interface IShoppingCartService {
    ShoppingCart getCustomerCartById(Integer customerId);

    ShoppingCart addProductToCustomerCart(String cartId, Integer productId, Integer productQuantity);

    ShoppingCart decreaseProductQuantityFromCustomerCart(String cartId, Integer productId);

    OrderDTOOutput placeOrder(String cartId);
}
