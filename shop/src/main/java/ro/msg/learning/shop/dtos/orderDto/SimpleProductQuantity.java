package ro.msg.learning.shop.dtos.orderDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimpleProductQuantity implements Comparable<SimpleProductQuantity> {
    private Integer productId;
    private Integer productQuantity;

    @Override
    public int compareTo(SimpleProductQuantity auxProd) {
        return this.productId.compareTo(auxProd.productId);
    }
}
