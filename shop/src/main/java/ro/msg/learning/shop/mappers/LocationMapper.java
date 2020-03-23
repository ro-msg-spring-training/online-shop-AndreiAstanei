package ro.msg.learning.shop.mappers;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.dtos.LocationOrderDTOs.LocationDirectionsMatrixAPI;
import ro.msg.learning.shop.dtos.LocationOrderDTOs.SimplifiedLocationIdAndDistance;
import ro.msg.learning.shop.entities.EmbeddableAddress;
import ro.msg.learning.shop.entities.Location;

import java.util.ArrayList;
import java.util.List;

@Component
public class LocationMapper {
    public LocationDirectionsMatrixAPI mapLocationToDirectionsMatrixLocation(Location location) {
        EmbeddableAddress embeddableAddress = location.getEmbeddableAddress();
        return LocationDirectionsMatrixAPI.builder()
                .street(embeddableAddress.getStreetAddress())
                .city(embeddableAddress.getCity())
                .country(embeddableAddress.getCountry())
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
