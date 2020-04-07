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
public class OrderDTOOutput {
    private Integer id;
    private Integer customerId;
    private String createdAt;

    private String country;
    private String city;
    private String county;
    private String streetAddress;

    private List<SimpleProductQuantity> productsList;
}
