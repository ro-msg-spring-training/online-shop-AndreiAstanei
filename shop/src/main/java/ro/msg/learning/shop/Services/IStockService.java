package ro.msg.learning.shop.Services;

import ro.msg.learning.shop.DTOs.stockDto.StockDTOOutput;

import java.util.List;

public interface IStockService {
    List<StockDTOOutput> getStocks(Integer locationId);
}
