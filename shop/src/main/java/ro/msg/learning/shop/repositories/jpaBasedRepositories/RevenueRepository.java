package ro.msg.learning.shop.repositories.jpaBasedRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.entities.Revenue;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface RevenueRepository extends JpaRepository<Revenue, Integer> {
    List<Revenue> findAllByDateIs(LocalDate givenDate);
}
