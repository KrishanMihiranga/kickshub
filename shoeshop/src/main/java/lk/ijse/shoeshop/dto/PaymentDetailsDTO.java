package lk.ijse.shoeshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDetailsDTO {
    private int cash;
    private int card;
    private double CashPercentageChange;
    private double CardPercentageChange;
}
