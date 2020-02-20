package ro.msg.learning.shop.DTOs.orderDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimpleProductQuantity {
    private Integer productId;
    private Integer productQuantity;

    @Override
    public boolean equals(Object obj) {
        return this.productId.equals(((SimpleProductQuantity) obj).productId) && this.productQuantity <= ((SimpleProductQuantity) obj).productQuantity;
    }
}
