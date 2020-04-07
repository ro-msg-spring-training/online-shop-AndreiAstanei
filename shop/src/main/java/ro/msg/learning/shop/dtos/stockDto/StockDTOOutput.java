package ro.msg.learning.shop.dtos.stockDto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
@JsonPropertyOrder({"id", "productId", "productQuantity", "locationId"})
public class StockDTOOutput {
    private Integer id;
    private Integer productId;
    private Integer productQuantity;
    private Integer locationId;
}
