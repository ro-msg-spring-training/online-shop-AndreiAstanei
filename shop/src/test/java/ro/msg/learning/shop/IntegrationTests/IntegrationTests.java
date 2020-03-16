package ro.msg.learning.shop.IntegrationTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.DTOs.orderDto.OrderDTOInput;
import ro.msg.learning.shop.DTOs.orderDto.OrderDTOOutput;
import ro.msg.learning.shop.DTOs.orderDto.SimpleProductQuantity;
import ro.msg.learning.shop.Entities.Order;
import ro.msg.learning.shop.Repositories.*;
import ro.msg.learning.shop.configuration.OrderStrategyConfiguration;
import ro.msg.learning.shop.exceptions.OrderPlacingException;
import ro.msg.learning.shop.mappers.LocationMapper;
import ro.msg.learning.shop.mappers.OrderDetailMapper;
import ro.msg.learning.shop.mappers.OrderMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IntegrationTests {
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private LocationMapper locationMapper;
    @Autowired
    private ObjectMapper objectMapper;

    private OrderStrategyConfiguration orderStrategyConfiguration;

    @Before
    public void initialize() {
        orderDetailMapper = new OrderDetailMapper();
        orderMapper = new OrderMapper(orderDetailMapper);
        orderStrategyConfiguration = new OrderStrategyConfiguration(stockRepository, customerRepository, locationRepository, orderRepository, orderDetailRepository, productRepository, orderMapper, locationMapper, objectMapper);
    }

    @Test
    @Transactional
    public void CreateOrderSuccessfully() {
        List<SimpleProductQuantity> simpleProductQuantityList = new ArrayList<>();
        simpleProductQuantityList.add(SimpleProductQuantity.builder().productId(1).productQuantity(1).build());

        OrderDTOInput orderDTOInput = OrderDTOInput.builder()
                .customerId(1)
                .createdAt("2020-03-09 11:37:00")
                .order_timestamp(-120)
                .addressCountry("Romania")
                .addressCounty("Timis")
                .addressCity("Timisoara")
                .addressStreetAddress("Bvd Cetatii nr 93")
                .productsList(simpleProductQuantityList)
                .build();

        OrderDTOOutput orderGenerationResult = null;
        try {
            orderGenerationResult = orderStrategyConfiguration.generateOrderByStrategy(orderDTOInput);

            Optional<Order> createdOrderInDB = orderRepository.findById(orderGenerationResult.getId());

            Assert.assertNotNull(createdOrderInDB);

            if (createdOrderInDB.isPresent()) {
                Order createdOrder = createdOrderInDB.get();

                Assert.assertEquals(createdOrder.getAddressCity(), "Timisoara");

                Assert.assertEquals(createdOrder.getOrderDetails().get(0).getProduct().getId(), Optional.of(1).get());
                Assert.assertEquals(createdOrder.getOrderDetails().get(0).getQuantity(), Optional.of(1).get());
            }
        } catch (OrderPlacingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    public void CreatingOrderWithMissingStock() {
        List<SimpleProductQuantity> simpleProductQuantityList = new ArrayList<>();
        simpleProductQuantityList.add(SimpleProductQuantity.builder().productId(1).productQuantity(10000).build());

        OrderDTOInput orderDTOInput = OrderDTOInput.builder()
                .customerId(1)
                .createdAt("2020-03-09 12:57:00")
                .order_timestamp(-120)
                .addressCountry("Romania")
                .addressCounty("Timis")
                .addressCity("Timisoara")
                .addressStreetAddress("Bvd Cetatii nr 93")
                .productsList(simpleProductQuantityList)
                .build();

        Assertions.assertThrows(OrderPlacingException.class, () -> {
            OrderDTOOutput orderGenerationResult = orderStrategyConfiguration.generateOrderByStrategy(orderDTOInput);
        });
    }
}
