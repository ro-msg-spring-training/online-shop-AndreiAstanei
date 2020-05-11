package ro.msg.learning.shop.repositories.jpaBasedRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.dtos.orderDto.SimpleProductQuantity;
import ro.msg.learning.shop.entities.Stock;

import java.util.List;

@Transactional(readOnly = true)
public interface StockRepository extends JpaRepository<Stock, Integer> {
    List<Stock> findStocksByLocation_Id(Integer locationID);

    @Query("select distinct st.location.id  from Stock st")
    List<Integer> getLocationsList();

    Stock findByLocation_IdAndProduct_Id(Integer locationId, Integer productId);

    @Query(nativeQuery = true,
            value = "select st.* from stock st where st.product_id = :productId and st.quantity >= :productQuantity group by st.quantity order by max(st.quantity) desc limit 1")
    Stock getStockForMaxProductQuantity(@Param("productId") Integer productId, @Param("productQuantity") Integer productQuantity);

    @Query(
            nativeQuery = true,
            value = "select st.location_id from stock st where st.product_id in :productsList and st.quantity >= :minimumQuantity group by st.location_id having count(st.location_id) >= :productsCount"
    )
    List<Integer> findLocationIdsForAllOrderedProducts(@Param("productsList") List<Integer> productsList, @Param("productsCount") Integer productsCount, @Param("minimumQuantity") Integer minimumQuantity);

    @Query(
            value = "select new ro.msg.learning.shop.dtos.orderDto.SimpleProductQuantity(st.product.id, st.quantity) from Stock as st where st.location.id = :locationId"
    )
    List<SimpleProductQuantity> getSPQListFromCurrentLocationStocks(@Param("locationId") Integer locationId);

    @Query(
            nativeQuery = true,
            value = "select sum(st.quantity) from stock st where st.product_id = :productId"
    )
    Integer getTotalQuantityForProduct(@Param("productId") Integer productId);
}
