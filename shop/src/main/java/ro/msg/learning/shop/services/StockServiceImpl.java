package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.dtos.stockDto.StockDTOOutput;
import ro.msg.learning.shop.mappers.StockMapper;
import ro.msg.learning.shop.repositories.StockRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
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
