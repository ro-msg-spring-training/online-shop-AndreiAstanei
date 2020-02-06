package ro.msg.learning.shop.mappers;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.DTOs.ProductDTO;
import ro.msg.learning.shop.Entities.Product;

@Component
public class ProductMapper {

    public ProductDTO mapProductToProductDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .description(product.getDescription())
                .imageUrl(product.getImageUrl())
                .name(product.getName())
                .price(product.getPrice())
                .productCategoryDescription(product.getCategory().getDescription())
                .productCategoryName(product.getCategory().getName())
                .productSupplierName(product.getSupplier().getName())
                .weight(product.getWeight())
                .build();
    }
}
