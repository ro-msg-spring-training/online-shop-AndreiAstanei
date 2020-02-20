package ro.msg.learning.shop.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.Entities.Stock;

import java.util.List;

@Transactional(readOnly = true)
@EnableJpaRepositories(basePackages = "ro.msg.learning.shop.Repositories")
public interface StockRepository extends JpaRepository<Stock, Integer> {
    List<Stock> findStocksByLocation_Id(Integer locationID);

    @Query("select distinct st.location.id  from Stock st")
    List<Integer> getLocationsList();

    Stock findByLocation_IdAndProduct_Id(Integer locationId, Integer productId);

    @Query(nativeQuery = true,
           value = "select st.* from stock st where st.product_id = :productId and st.quantity >= :productQuantity group by st.quantity order by max(st.quantity) desc limit 1")
    Stock getStockForMaxProductQuantity(@Param("productId") Integer productId, @Param("productQuantity") Integer productQuantity);
}
