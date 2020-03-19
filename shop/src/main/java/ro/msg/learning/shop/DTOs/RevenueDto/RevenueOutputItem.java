package ro.msg.learning.shop.DTOs.RevenueDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RevenueOutputItem {
    private Integer locationId;
    private String locationName;
}
