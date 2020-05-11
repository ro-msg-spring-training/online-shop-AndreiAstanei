package ro.msg.learning.shop.dtos.emailDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailProductListItem {
    private String productName;
    private String productCategory;
    private Integer productQuantity;
    private Double productPriceTotal;
}
