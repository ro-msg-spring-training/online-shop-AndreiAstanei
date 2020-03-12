package ro.msg.learning.shop.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.Entities.Customer;

import java.util.Optional;

@Transactional(readOnly = true)
@EnableJpaRepositories(basePackages = "ro.msg.learning.shop.Repositories")
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByUsernameEquals(String username);
}
