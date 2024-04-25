package lk.ijse.shoeshop.dto;

import lk.ijse.shoeshop.entity.SupplierEntity;
import lk.ijse.shoeshop.entity.enums.ItemCategories;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDTO implements SuperDTO{
    private String itemCode;
    private String description;
    private ItemCategories category;
    private String supplierName;
    private SupplierEntity supplier;
    private Double unitPriceSale;
    private Double unitPriceBuy;
    private Double expectedPrice;
    private Double profitMargin;
}
