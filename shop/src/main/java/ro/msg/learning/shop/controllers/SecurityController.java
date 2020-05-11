package ro.msg.learning.shop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.msg.learning.shop.entities.Customer;

@Controller
@RequestMapping
public class SecurityController {
    @GetMapping(value = "/error")
    public String getMappingError() {
        return "login";
    }

    @PostMapping(value = "/error")
    public String postMappingError() {
        return "login";
    }

    @GetMapping(value = "/login")
    public String displayLoginPage(@ModelAttribute(name = "customer") Customer customer) {
        return "login";
    }
}
