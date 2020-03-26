package ro.msg.learning.shop.mappers.jdbcMappers;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.entities.Product;
import ro.msg.learning.shop.repositories.jpaBasedRepositories.ProductCategoryRepository;
import ro.msg.learning.shop.repositories.jpaBasedRepositories.SupplierRepository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class ProductRowMapper implements RowMapper<Product> {
    private final ProductCategoryRepository productCategoryRepository;
    private final SupplierRepository supplierRepository;

    @Override
    public Product mapRow(ResultSet resultSet, int i) throws SQLException {
        return Product.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .price(resultSet.getDouble("price"))
                .weight(resultSet.getDouble("weight"))
                .imageUrl(resultSet.getString("image_url"))
                .category(productCategoryRepository.findById(resultSet.getInt("category_id")).get())
                .supplier(supplierRepository.findById(resultSet.getInt("supplier_id")).get())
                .build();
    }
}
