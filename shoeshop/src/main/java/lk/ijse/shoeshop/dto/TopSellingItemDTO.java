package lk.ijse.shoeshop.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopSellingItemDTO {
    @NotNull
    private String name;
    @NotNull
    private Double price;
    @NotNull
    private String image;
}
