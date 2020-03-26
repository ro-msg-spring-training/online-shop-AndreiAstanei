package ro.msg.learning.shop.repositories.jpaBasedRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.entities.Order;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByCreatedAtBetween(LocalDateTime startingTime, LocalDateTime endingTime);
}
