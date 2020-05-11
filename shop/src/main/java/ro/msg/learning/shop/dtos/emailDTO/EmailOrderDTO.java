package ro.msg.learning.shop.dtos.emailDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.msg.learning.shop.dtos.shoppingCartDTO.DeliveryAddress;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailOrderDTO {
    private Integer orderId;
    private String customerName;
    private DeliveryAddress deliveryAddress;
    private String orderCreationTime;
    private List<EmailProductListItem> productListItemList;
}
