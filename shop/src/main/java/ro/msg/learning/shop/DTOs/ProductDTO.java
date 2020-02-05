package ro.msg.learning.shop.DTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private Double weight;
    private String imageUrl;
    private String productCategoryName;
    private String productCategoryDescription;
    private String productSupplierName;
}
