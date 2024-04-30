package lk.ijse.shoeshop.dto;

import jakarta.validation.constraints.Null;
import lk.ijse.shoeshop.entity.SupplierEntity;
import lk.ijse.shoeshop.entity.enums.ItemCategories;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaveItemDTO {
    private String itemCode;
    private String description;
    private ItemCategories category;
    private String supplierName;
    private SupplierEntity supplier;
    private Double unitPriceSale;
    private Double unitPriceBuy;
    private Double expectedPrice;
    private Double profitMargin;
    @Null(message = "Image Id is auto generated")
    private String id;
    private String image;
}
