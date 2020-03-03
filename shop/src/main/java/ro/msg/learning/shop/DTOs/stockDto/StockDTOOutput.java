package ro.msg.learning.shop.DTOs.stockDto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonSerializableSchema
@JsonSerialize
public class StockDTOOutput {
    private Integer id;
    private Integer productId;
    private Integer productQuantity;
    private Integer locationId;
}
