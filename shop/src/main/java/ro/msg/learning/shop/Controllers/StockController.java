package ro.msg.learning.shop.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.msg.learning.shop.DTOs.stockDto.StockDTOOutput;
import ro.msg.learning.shop.Services.StockServiceImpl;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/stocks")
public class StockController {
    private final StockServiceImpl stockService;

    @RequestMapping(value = "/{locationId}", method = RequestMethod.GET, produces = {"text/csv"})
    @ResponseBody
    public List<StockDTOOutput> getStocksInCsv(@PathVariable("locationId") Integer locationId, HttpServletResponse response) {
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=stocks.csv");

        return stockService.getStocks(locationId);
    }
}
