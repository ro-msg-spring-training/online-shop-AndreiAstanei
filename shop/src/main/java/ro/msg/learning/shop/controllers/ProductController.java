package ro.msg.learning.shop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dtos.productDto.ProductDTO;
import ro.msg.learning.shop.exceptions.ProductNotCreatedException;
import ro.msg.learning.shop.exceptions.ProductNotFoundException;
import ro.msg.learning.shop.services.ProductServiceImpl;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/products")
public class ProductController {
    private final ProductServiceImpl productService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductDTO> getProducts() {
        return productService.getProducts();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDTO getProduct(@PathVariable("id") Integer id) throws ProductNotFoundException {
        return productService.getProduct(id);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteProduct(@PathVariable("id") Integer id) {
        return productService.deleteProductById(id);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDTO updateProduct(@Valid @RequestBody ProductDTO updatedProductValues) throws ProductNotFoundException {
        return productService.updateProduct(updatedProductValues);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDTO createProduct(@RequestBody ProductDTO newProductData) throws ProductNotCreatedException {
        return productService.createProduct(newProductData);
    }
}
