package lk.ijse.shoeshop.dto;

import jakarta.validation.constraints.NotNull;
import lk.ijse.shoeshop.entity.enums.Colors;
import lk.ijse.shoeshop.entity.enums.ItemCategories;
import lk.ijse.shoeshop.entity.enums.Sizes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RefundDetailsDTO {
    private String orderId;
    @NotNull
    private Colors color;
    @NotNull
    private ItemCategories itemCategory;
    @NotNull
    private String image;
    @NotNull
    private Sizes size;
    @NotNull
    private int qty;
    private Date date;
    @NotNull
    private String itemName;
    @NotNull
    private double price;
    @NotNull
    private String itemCode;
}
