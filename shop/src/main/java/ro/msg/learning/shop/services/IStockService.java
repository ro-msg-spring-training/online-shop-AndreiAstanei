package ro.msg.learning.shop.services;

import ro.msg.learning.shop.dtos.stockDto.StockDTOOutput;

import java.util.List;

public interface IStockService {
    List<StockDTOOutput> getStocks(Integer locationId);
}
