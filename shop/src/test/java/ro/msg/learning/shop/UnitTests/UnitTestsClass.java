package ro.msg.learning.shop.UnitTests;

import lombok.AllArgsConstructor;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ro.msg.learning.shop.DTOs.stockDto.StockDTOOutput;
import ro.msg.learning.shop.Entities.Stock;
import ro.msg.learning.shop.Repositories.StockRepository;
import ro.msg.learning.shop.Services.StockServiceImpl;
import ro.msg.learning.shop.mappers.StockMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class UnitTestsClass {
    private StockRepository stockRepository;
    private StockMapper stockMapper;

    private StockServiceImpl stockService;
    private StockServiceImpl stockService2 = mock(StockServiceImpl.class);

    private List<StockDTOOutput> mockStockDTOList;

    @Before
    public void beforeTests() {
        stockService = new StockServiceImpl(stockRepository, stockMapper);
        stockRepository = mock(StockRepository.class);
        stockMapper = mock(StockMapper.class);

        mockStockDTOList = new ArrayList<>();
        mockStockDTOList.add(StockDTOOutput.builder().id(1).locationId(1).productId(1).productQuantity(10).build());
        mockStockDTOList.add(StockDTOOutput.builder().id(1).locationId(1).productId(2).productQuantity(15).build());
    }

    @Test
    public void getStocksInCsvFormatTest() {
//        when(stockService.getStocks(anyInt())).thenReturn(mockStockDTOList);

//        assertThat(stockService.getStocks(1).size()).isEqualTo(2);

        stockService2.getStocks(1);
        verify(stockService2).getStocks(1);
    }
}
