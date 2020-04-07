package ro.msg.learning.shop.repositories.jpaBasedRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.entities.ProductCategory;

@Transactional(readOnly = true)
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    ProductCategory findByNameEquals(String categoryName);
}
