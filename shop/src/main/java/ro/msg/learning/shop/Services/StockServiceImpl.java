package ro.msg.learning.shop.Services;

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
        List<StockDTOOutput> collect = stockRepository.findStocksByLocation_Id(locationId).stream().map((stock) -> {
            StockDTOOutput stockDto = stockMapper.mapStockToStockDTOOutput(stock);
            return stockDto;
        }).collect(Collectors.toList());

        return collect;
    }
}
