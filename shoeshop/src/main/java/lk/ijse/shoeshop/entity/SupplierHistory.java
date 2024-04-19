package lk.ijse.shoeshop.entity;

import jakarta.persistence.*;
import lk.ijse.shoeshop.entity.enums.Color;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "supplier-history")
public class SupplierHistory {
    @Id
    private String supplierHistoryId;
    private Date suppliedDate;
    @Enumerated(EnumType.STRING)
    private Color color;
    private Integer totalQty;
    private Double totalValue;
}
