package ro.msg.learning.shop.dtos.orderDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessedOrderProduct {
    private Integer locationId;
    private Integer productId;
    private Integer productQuantity;
}
