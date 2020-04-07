package ro.msg.learning.shop.dtos.productDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Double weight;
    private String imageUrl;
    private String productCategoryName;
    private String productSupplierName;
}
