package lk.ijse.shoeshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "supplier-history")
public class SupplierHistory {
    @Id
    private String supplierHistoryId;
    private Date suppliedDate;
    private Integer totalQty;
    private Double totalValue;
    @ManyToOne
    private Supplier supplier;
    @OneToMany(mappedBy = "supplyItemId.supplierHistory", cascade = CascadeType.ALL)
    private List<SupplierHistoryItem> supplierHistoryItems;
}
