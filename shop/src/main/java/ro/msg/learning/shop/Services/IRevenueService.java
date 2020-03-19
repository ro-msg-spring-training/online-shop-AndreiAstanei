package ro.msg.learning.shop.Services;

import ro.msg.learning.shop.DTOs.RevenueDto.RevenueDTOInput;
import ro.msg.learning.shop.DTOs.RevenueDto.RevenueDTOOutput;

public interface IRevenueService {
    RevenueDTOOutput getRevenueListByDate(RevenueDTOInput givenDate);

    void generateRevenueListForCurrentDay();
}
