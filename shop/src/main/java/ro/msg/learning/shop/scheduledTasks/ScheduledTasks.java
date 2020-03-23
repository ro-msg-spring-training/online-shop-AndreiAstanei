package ro.msg.learning.shop.scheduledTasks;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.services.RevenueService;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {
    private final RevenueService revenueService;

    @Scheduled(cron = "${schedule_cron_expression}")
    public void reportCurrentTime() {
        revenueService.generateRevenueListForCurrentDay();
    }
}
