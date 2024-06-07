package lk.ijse.shoeshop.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDetailsDTO {
    @NotNull
    private int cash;
    @NotNull
    private int card;
    @NotNull
    private double CashPercentageChange;
    @NotNull
    private double CardPercentageChange;
}
