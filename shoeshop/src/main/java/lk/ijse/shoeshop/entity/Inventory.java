package lk.ijse.shoeshop.entity;

import jakarta.persistence.*;
import lk.ijse.shoeshop.entity.enums.Color;
import lk.ijse.shoeshop.entity.enums.Size;
import lk.ijse.shoeshop.entity.enums.StockStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    private String inventoryId;
    @Enumerated(EnumType.STRING)
    private Size size;
    private Integer originalQty;
    private Integer currentQty;
    @Enumerated(EnumType.STRING)
    private StockStatus status;
    @Enumerated(EnumType.STRING)
    private Color color;
    @ManyToOne
    private Item item;
    @ManyToOne
    private ItemImage itemImage;
}
