package ro.msg.learning.shop.orderStrategies;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dtos.orderDto.OrderDTOInput;
import ro.msg.learning.shop.dtos.orderDto.ProcessedOrderProduct;
import ro.msg.learning.shop.entities.*;
import ro.msg.learning.shop.mappers.LocationMapper;
import ro.msg.learning.shop.mappers.OrderMapper;
import ro.msg.learning.shop.repositories.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Component
@RequiredArgsConstructor
public class OrderGenerator {
    @Autowired
    protected LocationRepository locationRepository;
    // For the rest of the strategies
    @Autowired
    protected StockRepository stockRepository;
    @Autowired
    protected OrderMapper orderMapper;
    @Autowired
    protected LocationMapper locationMapper;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ProductRepository productRepository;

    protected Order placeOrder(List<ProcessedOrderProduct> readyToBeProccessedProducts, OrderDTOInput inputData) {
        Order responseOrder;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Integer offset = ZonedDateTime.now().getOffset().getTotalSeconds() / 60; // Local timezone offset for server(UTC)

        // *** Adding the customer
        Customer orderCustomer = null;
        if (customerRepository.findById(inputData.getCustomerId()).isPresent()) {
            orderCustomer = customerRepository.findById(inputData.getCustomerId()).get();
        }

        // *** Setting the locations for the order
        Set<Location> orderLocation = new HashSet<>();
        readyToBeProccessedProducts.forEach(item -> {
            if (locationRepository.findById(item.getLocationId()).isPresent()) {
                orderLocation.add(locationRepository.findById(item.getLocationId()).get());
            }
        });

        // *** Creating the order
        EmbeddableAddress orderAddress = EmbeddableAddress.builder()
                .country(inputData.getAddressCountry())
                .county(inputData.getAddressCounty())
                .city(inputData.getAddressCity())
                .streetAddress(inputData.getAddressStreetAddress())
                .build();

        Order finalUserOrder = Order.builder()
                .createdAt(LocalDateTime.parse(inputData.getCreatedAt(), formatter))
                .order_timestamp(offset + inputData.getOrder_timestamp())   //calculating the offset for the client based on the client's timezone and the server's timezone
                .customer(orderCustomer)
                .shippedFrom(orderLocation)
                .embeddableAddress(orderAddress)
                .build();

        // Saving the new Order in the database
        responseOrder = orderRepository.save(finalUserOrder);

        // *** Updating the stocks in the database
        readyToBeProccessedProducts.forEach(item -> {
            Stock stock = stockRepository.findByLocation_IdAndProduct_Id(item.getLocationId(), item.getProductId());
            stock.setQuantity(stock.getQuantity() - item.getProductQuantity());
            stockRepository.save(stock);
        });

        // *** Creating the required entries in the order history table
        Order finalResponseOrder = responseOrder;
        readyToBeProccessedProducts.forEach(item -> {
            Product tempProduct = new Product();
            if (productRepository.findById(item.getProductId()).isPresent()) {
                tempProduct = productRepository.findById(item.getProductId()).get();
            }

            OrderDetail orderDetail = OrderDetail.builder()
                    .quantity(item.getProductQuantity())
                    .order(finalResponseOrder)
                    .product(tempProduct)
                    .build();

            orderDetailRepository.save(orderDetail);
        });

        // Updating the order with the newly created OrderDetails(more like history actually)
        List<OrderDetail> orderDetails = orderDetailRepository.findAllByOrder_Id(finalResponseOrder.getId());
        finalResponseOrder.setOrderDetails(orderDetails);

        return orderRepository.save(finalResponseOrder);
    }
}
