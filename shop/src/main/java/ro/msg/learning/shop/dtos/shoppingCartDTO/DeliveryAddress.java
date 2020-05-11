package ro.msg.learning.shop.dtos.shoppingCartDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryAddress {
    private String country;
    private String county;
    private String city;
    private String streetAddress;
}
