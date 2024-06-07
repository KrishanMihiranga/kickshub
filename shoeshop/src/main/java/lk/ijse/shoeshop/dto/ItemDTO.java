package lk.ijse.shoeshop.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lk.ijse.shoeshop.entity.SupplierEntity;
import lk.ijse.shoeshop.entity.enums.Gender;
import lk.ijse.shoeshop.entity.enums.ItemCategories;
import lk.ijse.shoeshop.entity.enums.Occasion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDTO implements SuperDTO{
    @Null
    private String itemCode;
    @NotNull
    private String description;
    @NotNull
    private ItemCategories category;
    @NotNull
    private Occasion occasion;
    @NotNull
    private Gender gender;
    @NotNull
    private String supplierName;
    @NotNull
    private SupplierEntity supplier;
    @NotNull
    private Double unitPriceSale;
    @NotNull
    private Double unitPriceBuy;
    @NotNull
    private Double expectedPrice;
    @NotNull
    private Double profitMargin;
}
