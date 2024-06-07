package lk.ijse.shoeshop.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CheckCustomerDTO {
    @NotNull
    private String name;
    @NotNull
    private String contact;
}
