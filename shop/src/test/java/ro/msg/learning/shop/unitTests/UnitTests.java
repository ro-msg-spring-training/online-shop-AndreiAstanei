package ro.msg.learning.shop.unitTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import ro.msg.learning.shop.configuration.OrderStrategyConfiguration;
import ro.msg.learning.shop.csvConverter.ConvertToCsv;
import ro.msg.learning.shop.dtos.orderDto.OrderDTOInput;
import ro.msg.learning.shop.dtos.orderDto.OrderDTOOutput;
import ro.msg.learning.shop.dtos.orderDto.SimpleProductQuantity;
import ro.msg.learning.shop.dtos.stockDto.StockDTOOutput;
import ro.msg.learning.shop.entities.*;
import ro.msg.learning.shop.exceptions.OrderPlacingException;
import ro.msg.learning.shop.mappers.LocationMapper;
import ro.msg.learning.shop.mappers.OrderDetailMapper;
import ro.msg.learning.shop.mappers.OrderMapper;
import ro.msg.learning.shop.mappers.StockMapper;
import ro.msg.learning.shop.repositories.*;
import ro.msg.learning.shop.services.OrderServiceImpl;
import ro.msg.learning.shop.services.StockServiceImpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UnitTests {
    private static final String CSV_DATA = "id,productId,productQuantity,locationId\n1,1,30,1\n";
    int mockLocationId;
    List<Stock> mockStockList;
    List<StockDTOOutput> mockStockDtoList;
    // CSV TESTS
    private StockRepository stockRepository = Mockito.mock(StockRepository.class);
    private StockServiceImpl stockService = new StockServiceImpl(stockRepository, new StockMapper());
    private ConvertToCsv<StockDTOOutput> convertToCsv;

    // ORDER TEST
    private CustomerRepository customerRepository = mock(CustomerRepository.class);
    private LocationRepository locationRepository = mock(LocationRepository.class);
    private OrderRepository orderRepository = mock(OrderRepository.class);
    private OrderDetailRepository orderDetailRepository = mock(OrderDetailRepository.class);
    private ProductRepository productRepository = mock(ProductRepository.class);
    private OrderMapper orderMapper = mock(OrderMapper.class);
    private OrderDetailMapper orderDetailMapper = mock(OrderDetailMapper.class);
    private LocationMapper locationMapper = mock(LocationMapper.class);
    private ObjectMapper objectMapper = mock(ObjectMapper.class);

    private OrderStrategyConfiguration orderStrategyConfiguration = new OrderStrategyConfiguration(stockRepository, customerRepository, locationRepository, orderRepository, orderDetailRepository, productRepository, orderMapper, locationMapper, objectMapper);
    private OrderServiceImpl orderService = new OrderServiceImpl(orderStrategyConfiguration, orderRepository, orderMapper);

    private OrderDTOInput orderDTOInput;
    private List<OrderDTOOutput> resultOrdersList;
    private List<SimpleProductQuantity> simpleProductQuantities;
    private List<Integer> locationsList;


    @Before
    public void beforeEachTest() {
        mockStockList = new ArrayList<>();
        mockStockDtoList = new ArrayList<>();
        convertToCsv = new ConvertToCsv<>();
        simpleProductQuantities = new ArrayList<>();

        locationsList = new ArrayList<>();
        locationsList.add(1);
        locationsList.add(2);
    }

    // Pass
    @Test
    public void exampleTest() {
        mockLocationId = 3;
        mockStockList.add(Stock.builder().id(1).quantity(2).product(Product.builder().id(1).build()).location(Location.builder().id(mockLocationId).build()).build());
        mockStockList.add(Stock.builder().id(2).quantity(3).product(Product.builder().id(2).build()).location(Location.builder().id(mockLocationId).build()).build());

        mockStockDtoList.add(StockDTOOutput.builder().id(1).productId(1).productQuantity(2).locationId(mockLocationId).build());
        mockStockDtoList.add(StockDTOOutput.builder().id(2).productId(2).productQuantity(3).locationId(mockLocationId).build());

        // Assertion test
        when((stockRepository.findStocksByLocation_Id(anyInt()))).thenReturn(mockStockList);
        Assert.assertEquals(stockService.getStocks(mockLocationId), mockStockDtoList);
    }

    // Pass
    @Test
    public void exampleTest2() {
        StockServiceImpl mockStockService = mock(StockServiceImpl.class);
        // Verifying test
        mockStockService.getStocks(1);
        verify(mockStockService).getStocks(1);
    }

    // Pass
    @Test
    public void importFromCsvTest() throws IOException {
        List<StockDTOOutput> expectedResult = new ArrayList<>();
        expectedResult.add(StockDTOOutput.builder().id(1).productId(1).productQuantity(30).locationId(1).build());

        InputStream inputStream = new ByteArrayInputStream(CSV_DATA.getBytes());
        Assert.assertEquals(expectedResult, convertToCsv.fromCsv(StockDTOOutput.class, inputStream));
    }

    // Pass
    @Test
    public void exportToCsvTest() throws IOException {
        List<StockDTOOutput> existingStocks = new ArrayList<>();
        existingStocks.add(StockDTOOutput.builder().id(1).productId(1).productQuantity(30).locationId(1).build());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        convertToCsv.toCsv(StockDTOOutput.class, existingStocks, outputStream);

        Assert.assertEquals(CSV_DATA, new String(outputStream.toByteArray()));
    }

    // Pass
    @Test
    public void placeOrderSingleLocationTest() {
        // Stocks -> location 1 prod 1 quant 10, prod 2 quant 10
        //        -> Location 2 prod 1 quant 5, prod 2 quant 15
        mockStockList.add(Stock.builder().id(1).quantity(10).product(Product.builder().id(1).build()).location(Location.builder().id(1).build()).build());
        mockStockList.add(Stock.builder().id(2).quantity(10).product(Product.builder().id(2).build()).location(Location.builder().id(1).build()).build());
        mockStockList.add(Stock.builder().id(3).quantity(5).product(Product.builder().id(1).build()).location(Location.builder().id(2).build()).build());
        mockStockList.add(Stock.builder().id(4).quantity(15).product(Product.builder().id(2).build()).location(Location.builder().id(2).build()).build());

        simpleProductQuantities.add(SimpleProductQuantity.builder().productId(1).productQuantity(1).build());
        simpleProductQuantities.add(SimpleProductQuantity.builder().productId(2).productQuantity(1).build());

        when(stockRepository.getLocationsList()).thenReturn(locationsList);
        when(stockRepository.findStocksByLocation_Id(anyInt())).thenReturn(mockStockList);
        when(orderDetailMapper.mapOrderDetailToSimpleProductQuantityList(anyList())).thenReturn(simpleProductQuantities);
        when(orderMapper.mapOrderToOrderDTOOutput(any(Order.class))).thenReturn(OrderDTOOutput.builder().productsList(simpleProductQuantities).build());

        // all repositories
        when(stockRepository.findByLocation_IdAndProduct_Id(1, 1)).thenReturn(mockStockList.get(0));
        when(stockRepository.findByLocation_IdAndProduct_Id(1, 2)).thenReturn(mockStockList.get(1));
        when(stockRepository.findByLocation_IdAndProduct_Id(2, 1)).thenReturn(mockStockList.get(2));
        when(stockRepository.findByLocation_IdAndProduct_Id(2, 2)).thenReturn(mockStockList.get(3));

        when(orderRepository.save(any(Order.class))).thenReturn(Order.builder().id(1).build());
        List<OrderDetail> orderDetailsList = new ArrayList<>();
        when(orderDetailRepository.findAllByOrder_Id(anyInt())).thenReturn(orderDetailsList);

        orderDTOInput = OrderDTOInput.builder()
                .productsList(simpleProductQuantities).order_timestamp(0).customerId(1).createdAt("2020-03-06 10:10:10")
                .addressCity("City").addressCountry("Country").addressCounty("County").addressStreetAddress("Street")
                .build();

        try {
            Assert.assertEquals(orderService.generateOrder(orderDTOInput).getProductsList(), simpleProductQuantities);
        } catch (OrderPlacingException e) {
            e.printStackTrace();
        }
    }
}