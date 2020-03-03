package ro.msg.learning.shop.mappers;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.DTOs.stockDto.StockDTOOutput;
import ro.msg.learning.shop.Entities.Stock;

@Component
public class StockMapper {
    public StockDTOOutput mapStockToStockDTOOutput(Stock stock) {
        return StockDTOOutput.builder()
                .id(stock.getId())
                .productId(stock.getProduct().getId())
                .productQuantity(stock.getQuantity())
                .locationId(stock.getLocation().getId())
                .build();
    }
}
