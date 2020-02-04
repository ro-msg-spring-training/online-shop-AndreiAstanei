package ro.msg.learning.shop.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.Entities.ProductCategory;

@Transactional(readOnly = true)
@EnableJpaRepositories(basePackages = "ro.msg.learning.shop.Repositories")
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {    }
