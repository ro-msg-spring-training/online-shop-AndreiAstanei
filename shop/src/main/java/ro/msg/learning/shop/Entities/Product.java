package ro.msg.learning.shop.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Id
    private String name;

    @Id
    private String description;
    private BigDecimal price;
    private Double weight;
    private String imageUrl;

    @ManyToOne
    private ProductCategory category;

    @ManyToOne
    private Supplier supplier;

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    private List<Stock> stocks;
}
