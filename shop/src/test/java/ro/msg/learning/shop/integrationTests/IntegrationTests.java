package ro.msg.learning.shop.integrationTests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.dtos.orderDto.OrderDTOInput;
import ro.msg.learning.shop.dtos.orderDto.OrderDTOOutput;
import ro.msg.learning.shop.dtos.orderDto.SimpleProductQuantity;
import ro.msg.learning.shop.entities.Order;
import ro.msg.learning.shop.exceptions.OrderPlacingException;
import ro.msg.learning.shop.orderStrategies.OrderPlacingStrategiesInterface;
import ro.msg.learning.shop.repositories.jpaBasedRepositories.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IntegrationTests {
    @Autowired
    private OrderRepository orderRepository;

    // Strategy injections
    @Autowired
    private OrderPlacingStrategiesInterface orderPlacingStrategiesInterface;

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
            orderGenerationResult = orderPlacingStrategiesInterface.generateOrder(orderDTOInput);

            Optional<Order> createdOrderInDB = orderRepository.findById(orderGenerationResult.getId());

            Assert.assertNotNull(createdOrderInDB);

            if (createdOrderInDB.isPresent()) {
                Order createdOrder = createdOrderInDB.get();

                Assert.assertEquals(createdOrder.getEmbeddableAddress().getCity(), "Timisoara");

                Assert.assertEquals(createdOrder.getOrderDetails().get(0).getProduct().getId(), Optional.of(1).get());
                Assert.assertEquals(createdOrder.getOrderDetails().get(0).getQuantity(), Optional.of(1).get());
            }
        } catch (OrderPlacingException e) {
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
            OrderDTOOutput orderGenerationResult = orderPlacingStrategiesInterface.generateOrder(orderDTOInput);
        });
    }
}
