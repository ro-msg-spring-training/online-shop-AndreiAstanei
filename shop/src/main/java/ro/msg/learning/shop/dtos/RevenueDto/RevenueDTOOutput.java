package ro.msg.learning.shop.dtos.RevenueDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RevenueDTOOutput {
    private List<RevenueOutputItem> locationsReport;
    private LocalDate reportDate;
    private Double totalRevenue;
}
