package ro.msg.learning.shop.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class SecurityController {
    @GetMapping()
    public String securityBlankPage() {
        return "Welcome to this wonderful Spring Application!";
    }

    @GetMapping(value = "error")
    public String securityBlankError() {
        return "An error occurred while trying to login!";
    }
}
