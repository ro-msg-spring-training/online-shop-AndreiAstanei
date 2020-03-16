package ro.msg.learning.shop.DTOs.LocationOrderDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SimplifiedLocationIdAndDistance {
    private Integer id;
    private Double distance;
}
