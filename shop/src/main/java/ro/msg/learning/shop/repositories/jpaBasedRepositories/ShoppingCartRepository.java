package ro.msg.learning.shop.repositories.jpaBasedRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.entities.shoppingCartEntities.ShoppingCart;

@Transactional(readOnly = true)
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {
}
