package ro.msg.learning.shop.services;

import ro.msg.learning.shop.dtos.RevenueDto.RevenueDTOInput;
import ro.msg.learning.shop.dtos.RevenueDto.RevenueDTOOutput;

public interface IRevenueService {
    RevenueDTOOutput getRevenueListByDate(RevenueDTOInput givenDate);

    void generateRevenueListForCurrentDay();
}
