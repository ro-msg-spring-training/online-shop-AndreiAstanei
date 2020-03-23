package ro.msg.learning.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.entities.Location;

@Transactional(readOnly = true)
public interface LocationRepository extends JpaRepository<Location, Integer> {
}
