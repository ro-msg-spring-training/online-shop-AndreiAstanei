package ro.msg.learning.shop.repositories.jdbcBasedRepositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.entities.Product;
import ro.msg.learning.shop.mappers.jdbcMappers.ProductRowMapper;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JDBCBasedProductRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ProductRowMapper productRowMapper;
    private String query;

    public List<Product> findAll() {
        query = "select * from product";

        return jdbcTemplate.query(query, productRowMapper);
    }

    public Optional<Product> findById(Integer id) {
        query = "select * from product where id = ?";

        return Optional.ofNullable(jdbcTemplate.queryForObject(query, new Object[]{id}, productRowMapper));
    }

    public void deleteById(Integer id) {
        query = "delete from product where id = ?";

        jdbcTemplate.update(query, id);
    }

    public Optional<Product> save(Product product) {
        int productId;

        if (product.getId() != null) {
            query = "update product set name = ?, description = ?, image_url = ?, price = ?, weight = ?, category_id = ?, supplier_id = ? where id = ?";
            jdbcTemplate.update(query, product.getName(), product.getDescription(), product.getImageUrl(), product.getPrice(), product.getWeight(), product.getCategory().getId(), product.getSupplier().getId(), product.getId());
            productId = product.getId();
        } else {
            query = "insert into product (name, description, image_url, price, weight, category_id, supplier_id) values(?, ?, ?, ?, ?, ?, ?)";

            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, product.getName());
                ps.setString(2, product.getDescription());
                ps.setString(3, product.getImageUrl());
                ps.setDouble(4, product.getPrice());
                ps.setDouble(5, product.getWeight());
                ps.setInt(6, product.getCategory().getId());
                ps.setInt(7, product.getSupplier().getId());

                return ps;
            }, keyHolder);

            productId = Objects.requireNonNull(keyHolder.getKey()).intValue();
        }

        query = "select * from product where id = ?";

        return Optional.ofNullable(jdbcTemplate.queryForObject(query, new Object[]{productId}, productRowMapper));
    }
}
