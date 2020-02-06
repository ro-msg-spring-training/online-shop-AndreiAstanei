package ro.msg.learning.shop.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
                                                            // Apple Iphone (emag/cel  -  huse/telefoane
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private Double weight;
    private String imageUrl;
    private String productCategoryName;
    private String productCategoryDescription;
    private String productSupplierName;
}
