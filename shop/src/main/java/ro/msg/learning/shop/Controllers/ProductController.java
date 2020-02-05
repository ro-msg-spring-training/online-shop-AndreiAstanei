package ro.msg.learning.shop.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.msg.learning.shop.DTOs.ProductDTO;
import ro.msg.learning.shop.Services.ProductServiceImpl;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    @ResponseBody
    public List<ProductDTO> getProducts() {
        return productService.getProducts();
    }
}
