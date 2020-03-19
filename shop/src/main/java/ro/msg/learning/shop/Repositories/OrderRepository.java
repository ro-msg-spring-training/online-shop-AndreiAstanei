package ro.msg.learning.shop.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.Entities.Order;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
@EnableJpaRepositories(basePackages = "ro.msg.learning.shop.Repositories")
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByCreatedAtBetween(LocalDateTime startingTime, LocalDateTime endingTime);
}
