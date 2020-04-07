package ro.msg.learning.shop.dtos.LocationOrderDTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class LocationOrderDTOInput implements Serializable {
    private List<String> distance;
    private List<String> time;
}
