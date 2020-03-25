package ro.msg.learning.shop.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.msg.learning.shop.orderStrategies.*;

@Configuration
@RequiredArgsConstructor
public class OrderStrategyConfiguration {
    @Value(value = "${order_selection_strategy}")
    private OrderStrategiesEnum selectedStrategy;

    @Value(value = "${directions_route_matrix_api_key}")
    private String routeMatrixApiKey;

    @Bean
    public OrderPlacingStrategiesInterface generateOrderByStrategy() {
        switch (selectedStrategy) {
            case MOST_ABUNDANT:
                return new MostAbundantStrategy();

            case PROXIMITY_TO_LOCATION:
                return new ProximityToLocationStrategy(routeMatrixApiKey);

            default:
                return new SingleLocationStrategy();
        }
    }
}