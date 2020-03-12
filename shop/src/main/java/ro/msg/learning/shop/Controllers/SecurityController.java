package ro.msg.learning.shop.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/")
public class SecurityController {
    @GetMapping()
    @ResponseBody
    public String securityBlankPage() {
        return "Welcome to this wonderful Spring Application!";
    }

    @GetMapping(value = "error")
    @ResponseBody
    public String securityBlankError() {
        return "An error occured while trying to login!";
    }
}
