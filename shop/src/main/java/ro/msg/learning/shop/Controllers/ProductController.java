package ro.msg.learning.shop.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.DTOs.productDto.ProductDTO;
import ro.msg.learning.shop.Services.ProductServiceImpl;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/products")
public class ProductController {
    private final ProductServiceImpl productService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<ProductDTO> getProducts() {
        return productService.getProducts();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ProductDTO getProduct(@PathVariable("id") Integer id) {
        return productService.getProduct(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String deleteProduct(@PathVariable("id") Integer id) {
        return productService.deleteProductById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ProductDTO updateProduct(@RequestBody ProductDTO updatedProductValues) {
        return productService.updateProduct(updatedProductValues);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ProductDTO createProduct(@RequestBody ProductDTO newProductData) {
        return productService.createProduct(newProductData);
    }
}
