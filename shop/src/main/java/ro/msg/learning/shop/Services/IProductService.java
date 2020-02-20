package ro.msg.learning.shop.Services;

import ro.msg.learning.shop.DTOs.productDto.ProductDTO;

import java.util.List;

public interface IProductService {
    List<ProductDTO> getProducts();

    ProductDTO getProduct(Integer id);

    String deleteProductById(Integer id);

    ProductDTO updateProduct(ProductDTO newProductValues);

    ProductDTO createProduct(ProductDTO productData);
}
