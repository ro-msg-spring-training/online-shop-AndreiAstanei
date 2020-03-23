package ro.msg.learning.shop.dtos.LocationOrderDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LocationDirectionsMatrixAPI {
    private String street;
    private String city;
    private String country;

    public LocationDirectionsMatrixAPI(LocationDirectionsMatrixAPI location) {
        this.street = location.getStreet();
        this.city = location.getCity();
        this.country = location.getCountry();
    }

    @Override
    public String toString() {
        return "" + this.street + ", " + this.city + ", " + this.country + "";
    }
}
