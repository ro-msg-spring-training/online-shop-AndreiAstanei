package ro.msg.learning.shop.services;

import ro.msg.learning.shop.dtos.productDto.ProductDTO;
import ro.msg.learning.shop.exceptions.ProductNotCreatedException;
import ro.msg.learning.shop.exceptions.ProductNotFoundException;

import java.util.List;

public interface IProductService {
    List<ProductDTO> getProducts();

    ProductDTO getProduct(Integer id) throws ProductNotFoundException;

    String deleteProductById(Integer id);

    ProductDTO updateProduct(ProductDTO newProductValues) throws ProductNotFoundException;

    ProductDTO createProduct(ProductDTO productData) throws ProductNotCreatedException;
}
