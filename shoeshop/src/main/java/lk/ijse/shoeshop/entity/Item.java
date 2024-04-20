package lk.ijse.shoeshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "item")
public class Item {
    @Id
    private String itemCode;
    private String itemDescription;
    private String category;
    @ManyToOne
    private Supplier supplier;
    private Double unitPurchasePrice;
    private Double unitSellingPrice;
    private Double profitPerUnit;

    @OneToMany(mappedBy="saleItemId.item", cascade = CascadeType.ALL)
    private List<SaleItems> saleItems;

    @OneToMany(mappedBy = "supplyItemId.item", cascade = CascadeType.ALL)
    private List<SupplierHistoryItem> supplierHistoryItems;

    @OneToMany(mappedBy = "item" , cascade = CascadeType.ALL , orphanRemoval = true , fetch = FetchType.LAZY)
    private List<Inventory> inventoryList;
}
