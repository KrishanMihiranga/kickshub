package lk.ijse.shoeshop.entity;

import jakarta.persistence.*;
import lk.ijse.shoeshop.entity.enums.Color;
import lk.ijse.shoeshop.entity.enums.Size;
import lk.ijse.shoeshop.entity.key.SaleItemId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "sale-items")
@AssociationOverrides({
        @AssociationOverride(name = "saleItemId.sale", joinColumns = @JoinColumn(name = "orderId")),
        @AssociationOverride(name = "saleItemId.item", joinColumns = @JoinColumn(name = "itemCode"))
})
public class SaleItems {
    @Id
    private SaleItemId saleItemId;
    private Integer qty;
    @Enumerated(EnumType.STRING)
    private Size size;
    @Enumerated(EnumType.STRING)
    private Color color;
    private Double unitPrice;
    @ManyToOne
    private Refund refund;
}
