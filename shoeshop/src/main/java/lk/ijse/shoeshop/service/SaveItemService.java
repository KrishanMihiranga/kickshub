package lk.ijse.shoeshop.service;

import lk.ijse.shoeshop.dto.*;

import java.time.LocalDate;
import java.util.List;

public interface SaveItemService {
    SaveItemDTO saveItem(SaveItemDTO saveItemDTO);
    Double getTotalValueOfItemsSoldWithin24Hours(LocalDate date);
    Double getTotalValueOfItemsBuyWithin24hours(LocalDate date);
    Double getTotalIncomeWithin24Hours(LocalDate date);
    List<Top5DTO> getTop5Products(LocalDate date);
    TopSellingItemDTO getTopSellingProduct(LocalDate date);
    PaymentDetailsDTO totalPaymentMethods(LocalDate date);
}
