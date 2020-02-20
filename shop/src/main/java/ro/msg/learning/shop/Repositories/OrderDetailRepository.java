package ro.msg.learning.shop.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.Entities.OrderDetail;

import java.util.List;

@Transactional(readOnly = true)
@EnableJpaRepositories(basePackages = "ro.msg.learning.shop.Repositories")
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    List<OrderDetail> findAllByOrder_Id(Integer orderId);
}
