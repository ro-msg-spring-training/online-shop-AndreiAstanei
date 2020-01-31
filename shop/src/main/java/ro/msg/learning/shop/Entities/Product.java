package ro.msg.learning.shop.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

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

    @ManyToOne
    private ProductCategory Category;

    @ManyToOne
    private Supplier Supplier;
    private String ImageUrl;
}
