package lk.ijse.shoeshop.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lk.ijse.shoeshop.entity.enums.Color;
import lk.ijse.shoeshop.entity.enums.Size;
import lk.ijse.shoeshop.entity.enums.StockStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
//@Entity
//Need to implement composite keys
@Table(name = "inventory")
public class Inventory {
    @Enumerated(EnumType.STRING)
    private Size size;
    private Integer qty;
    private Integer maxQty;
    @Enumerated(EnumType.STRING)
    private StockStatus status;
    @Enumerated(EnumType.STRING)
    private Color color;
}
