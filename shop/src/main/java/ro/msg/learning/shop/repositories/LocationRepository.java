package ro.msg.learning.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.entities.Location;

import java.util.List;

@Transactional(readOnly = true)
public interface LocationRepository extends JpaRepository<Location, Integer> {
    @Query("select loc.id  from Location loc")
    List<Integer> getLocationsIds();
}
