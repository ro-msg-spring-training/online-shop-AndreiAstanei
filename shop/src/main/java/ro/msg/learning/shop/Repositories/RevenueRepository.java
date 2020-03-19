package ro.msg.learning.shop.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.Entities.Revenue;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
@EnableJpaRepositories(basePackages = "ro.msg.learning.shop.Repositories")
public interface RevenueRepository extends JpaRepository<Revenue, Integer> {
    List<Revenue> findAllByDateIs(LocalDate givenDate);
}
