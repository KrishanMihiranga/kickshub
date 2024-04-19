package lk.ijse.shoeshop.entity;

import jakarta.persistence.*;
import lk.ijse.shoeshop.entity.enums.Color;
import lk.ijse.shoeshop.entity.enums.Size;
import lk.ijse.shoeshop.entity.key.SupplyItemId;

import java.util.Date;

@Entity
@Table(name = "supplier-history-item")
@AssociationOverrides({
        @AssociationOverride(name = "supplyItemId.item", joinColumns = @JoinColumn(name = "itemCode")),
        @AssociationOverride(name = "supplyItemId.supplierHistory", joinColumns = @JoinColumn(name = "suppplyId"))
})
public class SupplierHistoryItem {
    @Id
    private SupplyItemId supplyItemId;
    private Integer suppliedQty;
    private Date suppliedDate;
    @Enumerated(EnumType.STRING)
    private Color color;
    @Enumerated(EnumType.STRING)
    private Size size;
}
