package ro.msg.learning.shop.mappers;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.DTOs.LocationOrderDTOs.LocationDirectionsMatrixAPI;
import ro.msg.learning.shop.DTOs.LocationOrderDTOs.SimplifiedLocationIdAndDistance;
import ro.msg.learning.shop.Entities.Location;

import java.util.ArrayList;
import java.util.List;

@Component
public class LocationMapper {
    public LocationDirectionsMatrixAPI mapLocationToDirectionsMatrixLocation(Location location) {
        return LocationDirectionsMatrixAPI.builder()
                .street(location.getAddressStreetAddress())
                .city(location.getAddressCity())
                .country(location.getAddressCountry())
                .build();
    }

    public List<SimplifiedLocationIdAndDistance> mapLocationsIdsAndDistanceData(List<Integer> locationsIds, List<Double> distanceData) {
        List<SimplifiedLocationIdAndDistance> resultList = new ArrayList<>();

        if(locationsIds.size() == distanceData.size()) {
            for(int i = 0; i < locationsIds.size(); i++) {
                resultList.add(SimplifiedLocationIdAndDistance.builder().id(locationsIds.get(i)).distance(distanceData.get(i)).build());
            }
        }

        return resultList;
    }
}
