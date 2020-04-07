package ro.msg.learning.shop.dtos.orderDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTOInput {
    private Integer customerId;
    private String createdAt;
    private Integer order_timestamp;

    private String addressCountry;
    private String addressCity;
    private String addressCounty;
    private String addressStreetAddress;

    private List<SimpleProductQuantity> productsList;
}
