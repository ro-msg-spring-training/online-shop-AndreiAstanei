package ro.msg.learning.shop.mappers;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dtos.orderDto.SimpleProductQuantity;
import ro.msg.learning.shop.entities.shoppingCartEntities.ShoppingCartItem;

@Component
public class ShoppingCartMapper {

    public SimpleProductQuantity mapShoppingCartItemToSimpleProductQuantity(ShoppingCartItem shoppingCartItem) {
        return SimpleProductQuantity.builder()
                .productId(shoppingCartItem.getProduct().getId())
                .productQuantity(shoppingCartItem.getQuantity())
                .build();
    }
}
