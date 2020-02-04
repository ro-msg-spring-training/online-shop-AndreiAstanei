package ro.msg.learning.shop.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;

    private String Name;
    private String Description;
    private BigDecimal Price;
    private Double Weight;
    private String ImageUrl;

    @ManyToOne
    private ProductCategory Category;

    @ManyToOne
    private Supplier Supplier;

    @OneToMany(mappedBy = "Product")
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "product")
    private List<Stock> stocks;
}
