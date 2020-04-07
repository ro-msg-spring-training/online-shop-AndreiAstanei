package ro.msg.learning.shop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.dtos.RevenueDto.RevenueDTOInput;
import ro.msg.learning.shop.dtos.RevenueDto.RevenueDTOOutput;
import ro.msg.learning.shop.services.revenueService.RevenueService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/revenues")
public class RevenueController {
    private final RevenueService revenueService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public RevenueDTOOutput getProducts(@RequestBody RevenueDTOInput givenDate) {
        return revenueService.getRevenueListByDate(givenDate);
    }
}
