package ro.msg.learning.shop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.msg.learning.shop.dtos.RevenueDto.RevenueDTOInput;
import ro.msg.learning.shop.dtos.RevenueDto.RevenueDTOOutput;
import ro.msg.learning.shop.dtos.RevenueDto.RevenueOutputItem;
import ro.msg.learning.shop.entities.Location;
import ro.msg.learning.shop.entities.Order;
import ro.msg.learning.shop.entities.Revenue;
import ro.msg.learning.shop.mappers.RevenueMapper;
import ro.msg.learning.shop.repositories.OrderRepository;
import ro.msg.learning.shop.repositories.RevenueRepository;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Transactional
@RequiredArgsConstructor
public class RevenueService implements IRevenueService {
    private static DecimalFormat customDoubleFormat = new DecimalFormat("#.##");
    private final RevenueMapper revenueMapper;
    private final RevenueRepository revenueRepository;
    private final OrderRepository orderRepository;

    @Override
    public RevenueDTOOutput getRevenueListByDate(RevenueDTOInput givenDate) {
        RevenueDTOOutput methodResponse = null;
        LocalDate date = revenueMapper.mapStringDateToLocalDate(givenDate.getGivenDate());

        // If the date is a valid one, continue
        if (date != null) {
            List<Revenue> revenueListForDate = revenueRepository.findAllByDateIs(date);

            // If there are any revenue values for the given date
            if (revenueListForDate.size() > 0) {
                List<RevenueOutputItem> revenueItems = new ArrayList<>();

                revenueListForDate.forEach(listItem -> {
                    revenueItems.add(RevenueOutputItem.builder().locationId(listItem.getLocation().getId()).locationName(listItem.getLocation().getName()).build());
                });


                methodResponse = RevenueDTOOutput.builder()
                        .locationsReport(revenueItems)
                        .reportDate(date)
                        .totalRevenue(Double.parseDouble(customDoubleFormat.format(revenueListForDate.get(0).getSum())))
                        .build();
            } else {
                methodResponse = RevenueDTOOutput.builder()
                        .locationsReport(Collections.EMPTY_LIST)
                        .reportDate(date)
                        .totalRevenue(0.0)
                        .build();
            }
        }

        return methodResponse;
    }

    @Override
    public void generateRevenueListForCurrentDay() {
        // This is the current date
        LocalDate currentDate = LocalDate.now();
        List<Revenue> generatedRevenueListForToday;

        // Checking to see whether the system has already generated the sales report for today or not
        List<Revenue> revenueListForCurrentDate = revenueRepository.findAllByDateIs(currentDate);
        if (revenueListForCurrentDate.size() == 0) {     // MUST be == 0
            // if the size of the list for today is 0, it means we are good to go for generating the report
            // here are all the orders placed today
            List<Order> orderListForToday = orderRepository.findAllByCreatedAtBetween(currentDate.atStartOfDay(), currentDate.plusDays(1).atStartOfDay());

            // next up we need to make the list of revenue objects based on the above list
            if (orderListForToday.size() > 0) {
                generatedRevenueListForToday = new ArrayList<>();

                // Locations list based on the data from the order list
                HashSet<Location> uniqueLocationsThatShippedProductsToday = new HashSet<>();
                AtomicReference<Double> totalSalesToday = new AtomicReference<>(0.0);

                // for each order placed today
                orderListForToday.forEach(order -> {
                    // for each location that shipped any products
                    order.getShippedFrom().forEach(shippingLocation -> {
                        uniqueLocationsThatShippedProductsToday.add(shippingLocation);
                    });

                    // and for each order detail, get the amount sold
                    order.getOrderDetails().forEach(orderDetail -> {
                        totalSalesToday.updateAndGet(v -> v + (orderDetail.getProduct().getPrice() * orderDetail.getQuantity()));
                    });
                });

                // Now, at the end, we have both the locations list and the sales figure. Next step is to generate the Revenue records,
                // based on the locations list
                uniqueLocationsThatShippedProductsToday.forEach(location -> {
                    revenueRepository.save(Revenue.builder()
                            .date(currentDate)
                            .sum(totalSalesToday.get())
                            .location(location)
                            .build());
                });
            }
        }
    }
}
