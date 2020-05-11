package ro.msg.learning.shop.repositories.jpaBasedRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.entities.shoppingCartEntities.ShoppingCartItem;

@Transactional(readOnly = true)
public interface ShoppingCartItemsRepository extends JpaRepository<ShoppingCartItem, Integer> {
}
