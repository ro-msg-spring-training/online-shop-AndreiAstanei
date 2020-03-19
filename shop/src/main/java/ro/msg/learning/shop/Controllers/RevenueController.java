package ro.msg.learning.shop.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.msg.learning.shop.DTOs.RevenueDto.RevenueDTOInput;
import ro.msg.learning.shop.DTOs.RevenueDto.RevenueDTOOutput;
import ro.msg.learning.shop.Services.RevenueService;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/revenues")
public class RevenueController {
    private final RevenueService revenueService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public RevenueDTOOutput getProducts(@RequestBody RevenueDTOInput givenDate) {
        return revenueService.getRevenueListByDate(givenDate);
    }
}
