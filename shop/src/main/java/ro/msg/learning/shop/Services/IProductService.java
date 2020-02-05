package ro.msg.learning.shop.Services;

import ro.msg.learning.shop.DTOs.ProductDTO;

import java.util.List;

public interface IProductService {
    List<ProductDTO> getProducts();
}
