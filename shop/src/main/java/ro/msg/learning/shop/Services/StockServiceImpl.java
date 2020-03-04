package ro.msg.learning.shop.Services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.DTOs.stockDto.StockDTOOutput;
import ro.msg.learning.shop.Repositories.StockRepository;
import ro.msg.learning.shop.mappers.StockMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements IStockService {
    private final StockRepository stockRepository;
    private final StockMapper stockMapper;

    @Override
    public List<StockDTOOutput> getStocks(Integer locationId) {
        return stockRepository.findStocksByLocation_Id(locationId).stream().map(stockMapper::mapStockToStockDTOOutput).collect(Collectors.toList());
    }
}
