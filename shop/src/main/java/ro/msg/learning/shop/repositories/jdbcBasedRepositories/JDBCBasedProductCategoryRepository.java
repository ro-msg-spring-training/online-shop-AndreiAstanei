package ro.msg.learning.shop.repositories.jdbcBasedRepositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.entities.ProductCategory;
import ro.msg.learning.shop.mappers.jdbcMappers.ProductCategoryRowMapper;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JDBCBasedProductCategoryRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ProductCategoryRowMapper productCategoryRowMapper;
    private String query;

    public List<ProductCategory> findAll() {
        query = "select * from product_category";

        return jdbcTemplate.query(query, productCategoryRowMapper);
    }

    public Optional<ProductCategory> findById(Integer id) {
        query = "select * from product_category where id = ?";

        return Optional.ofNullable(jdbcTemplate.queryForObject(query, new Object[]{id}, productCategoryRowMapper));
    }

    public void deleteById(Integer id) {
        query = "delete from product_category where id = ?";

        jdbcTemplate.update(query, id);
    }

    public Optional<ProductCategory> save(ProductCategory productCategory) {
        int categoryId;

        if (productCategory.getId() != null) {
            query = "update product_category set name = ?, description = ? where id = ?";
            jdbcTemplate.update(query, productCategory.getName(), productCategory.getDescription(), productCategory.getId());
            categoryId = productCategory.getId();
        } else {
            query = "insert into product_category (name, description) values(?, ?)";

            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, productCategory.getName());
                ps.setString(2, productCategory.getDescription());

                return ps;
            }, keyHolder);

            categoryId = Objects.requireNonNull(keyHolder.getKey()).intValue();
        }

        query = "select * from product_category where id = ?";

        return Optional.ofNullable(jdbcTemplate.queryForObject(query, new Object[]{categoryId}, productCategoryRowMapper));
    }

    public Optional<ProductCategory> findByNameEquals(String categoryName) {
        query = "select * from product_category where name = ? limit 1";

        return Optional.ofNullable(jdbcTemplate.queryForObject(query, new Object[]{categoryName}, productCategoryRowMapper));
    }
}
