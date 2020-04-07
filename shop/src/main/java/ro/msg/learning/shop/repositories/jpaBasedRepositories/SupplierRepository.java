package ro.msg.learning.shop.repositories.jpaBasedRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.entities.Supplier;

@Transactional(readOnly = true)
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    Supplier findByNameEquals(String supplierName);
}
