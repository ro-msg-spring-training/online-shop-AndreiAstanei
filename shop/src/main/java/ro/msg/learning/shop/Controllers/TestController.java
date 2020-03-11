package ro.msg.learning.shop.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;
import ro.msg.learning.shop.DTOs.orderDto.OrderDTOInput;
import ro.msg.learning.shop.DTOs.orderDto.OrderDTOOutput;
import ro.msg.learning.shop.DTOs.stockDto.StockDTOOutput;
import ro.msg.learning.shop.Services.OrderServiceImpl;
import ro.msg.learning.shop.Services.StockServiceImpl;
import ro.msg.learning.shop.exceptions.OrderPlacingException;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/tests")
public class TestController {
    private final OrderServiceImpl orderService;
    private final StockServiceImpl stockService;

    // Pass
    @RequestMapping(value = "/createOrderSuccessfully", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
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
    @RequestMapping(value = "/exportStocksSuccess", method = RequestMethod.GET, produces = {"text/csv"})
    @ResponseBody
    public List<StockDTOOutput> exportStocksSuccess() {
        return stockService.getStocks(1);
    }

    // Pass
    @RequestMapping(value = "/failToCreateOrderDueToMissingStock", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public OrderDTOOutput failToCreateOrderDueToMissingStock(@RequestBody OrderDTOInput orderDTOInputData) {
        try {
            return orderService.generateOrder(orderDTOInputData);
        } catch (OrderPlacingException ex) {
            throw new ResponseStatusException(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE, "Product quantity exceeded actual capacity", ex);
        }
    }

    // In progress - cancelled
    @RequestMapping(value = "/failToCreateorderDueToUnknownProductId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public OrderDTOOutput failToCreateorderDueToUnknownProductId(@RequestBody OrderDTOInput orderDTOInputData) {
        return null;
    }
}
