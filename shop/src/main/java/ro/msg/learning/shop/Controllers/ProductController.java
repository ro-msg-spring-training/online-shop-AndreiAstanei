package ro.msg.learning.shop.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.DTOs.ProductDTO;
import ro.msg.learning.shop.Services.ProductServiceImpl;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/products")
public class ProductController {


    private final ProductServiceImpl productService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<ProductDTO> getProducts() {
        return productService.getProducts();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ProductDTO getProduct(@PathVariable("id") Integer id) {
        return productService.getProduct(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteProduct(@PathVariable("id") Integer id) {
        return productService.deleteProductById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ProductDTO updateProduct( @RequestBody ProductDTO updatedProductValues) {
        return productService.updateProduct(updatedProductValues);
    }
}
