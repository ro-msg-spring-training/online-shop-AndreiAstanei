package ro.msg.learning.shop.DTOs.orderDto;

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

    private String addressCountry;
    private String addressCity;
    private String addressCounty;
    private String addressStreetAddress;

    private List<SimpleProductQuantity> productsList;
}
