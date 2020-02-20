package ro.msg.learning.shop.DTOs.orderDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessedOrderProduct {
    private Integer locationId;
    private Integer productId;
    private Integer productQuantity;
}
