package ro.msg.learning.shop.mappers;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Component
public class RevenueMapper {
    public LocalDate mapStringDateToLocalDate(String givenStringDate) {
        try {
            return LocalDate.parse(givenStringDate);
        } catch (DateTimeParseException ex) {
            System.out.println("RevenueMapper Exception 001 - Wrong LocalDate input!");
        }

        return null;
    }
}
