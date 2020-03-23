package ro.msg.learning.shop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ro.msg.learning.shop.dtos.orderDto.OrderDTOInput;
import ro.msg.learning.shop.dtos.orderDto.OrderDTOOutput;
import ro.msg.learning.shop.dtos.stockDto.StockDTOOutput;
import ro.msg.learning.shop.exceptions.OrderPlacingException;
import ro.msg.learning.shop.services.OrderServiceImpl;
import ro.msg.learning.shop.services.StockServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/tests")
public class TestController {
    private final OrderServiceImpl orderService;
    private final StockServiceImpl stockService;

    // Pass
    @PostMapping(value = "/createOrderSuccessfully", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public OrderDTOOutput createOrderSuccessfully(@RequestBody OrderDTOInput orderDTOInputData) {
        try {
            return orderService.generateOrder(orderDTOInputData);
        } catch (OrderPlacingException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Pass
    @GetMapping(value = "/exportStocksSuccess", produces = {"text/csv"})
    public List<StockDTOOutput> exportStocksSuccess() {
        return stockService.getStocks(1);
    }

    // Pass
    @PostMapping(value = "/failToCreateOrderDueToMissingStock", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderDTOOutput failToCreateOrderDueToMissingStock(@RequestBody OrderDTOInput orderDTOInputData) {
        try {
            return orderService.generateOrder(orderDTOInputData);
        } catch (OrderPlacingException ex) {
            throw new ResponseStatusException(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE, "Product quantity exceeded actual capacity", ex);
        }
    }

    // In progress - cancelled
    @RequestMapping(value = "/failToCreateorderDueToUnknownProductId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderDTOOutput failToCreateOrderDueToUnknownProductId(@RequestBody OrderDTOInput orderDTOInputData) {
        return null;
    }
}
