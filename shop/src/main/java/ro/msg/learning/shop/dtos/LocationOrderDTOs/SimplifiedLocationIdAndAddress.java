package ro.msg.learning.shop.dtos.LocationOrderDTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SimplifiedLocationIdAndAddress {
    private Integer id;
    private LocationDirectionsMatrixAPI location;
}
