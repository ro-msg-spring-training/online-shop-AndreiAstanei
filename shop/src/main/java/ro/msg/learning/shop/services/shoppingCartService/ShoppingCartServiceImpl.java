package ro.msg.learning.shop.services.shoppingCartService;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.dtos.orderDto.OrderDTOOutput;
import ro.msg.learning.shop.entities.mongoDbDocuments.QShoppingCart;
import ro.msg.learning.shop.entities.mongoDbDocuments.ShoppingCart;
import ro.msg.learning.shop.repositories.mongoDbBasedRepositories.ShoppingCartRepository;
import ro.msg.learning.shop.services.orderService.OrderServiceImpl;

@Service
@Transactional
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements IShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderServiceImpl orderService;

    @Override
    public ShoppingCart getCustomerCartById(Integer customerId) {
        QShoppingCart qShoppingCart = new QShoppingCart("Cart");
        BooleanExpression findByCustomerId = qShoppingCart.customer.id.eq(customerId);

        return shoppingCartRepository.findOne(findByCustomerId).get();
    }

    @Override
    public ShoppingCart addProductToCustomerCart(String cartId, Integer productId, Integer productQuantity) {
        return null;
    }

    @Override
    public ShoppingCart decreaseProductQuantityFromCustomerCart(String cartId, Integer productId) {
        return null;
    }

    @Override
    public OrderDTOOutput placeOrder(String cartId) {
        return null;
    }
}
