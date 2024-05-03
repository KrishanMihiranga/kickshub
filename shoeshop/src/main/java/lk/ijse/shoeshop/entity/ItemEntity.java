package lk.ijse.shoeshop.entity;

import jakarta.persistence.*;
import lk.ijse.shoeshop.entity.enums.Gender;
import lk.ijse.shoeshop.entity.enums.ItemCategories;
import lk.ijse.shoeshop.entity.enums.Occasion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "item")
public class ItemEntity {
    @Id
    private String itemCode;
    private String description;
    private ItemCategories category;
    private Occasion occasion;
    private Gender gender;
    private String supplierName;
    @ManyToOne
    private SupplierEntity supplier;
    private Double unitPriceSale;
    private Double unitPriceBuy;
    private Double expectedPrice;
    private Double profitMargin;
}
