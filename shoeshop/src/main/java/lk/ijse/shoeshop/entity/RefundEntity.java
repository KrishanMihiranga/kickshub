package lk.ijse.shoeshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "refund")
public class RefundEntity {
    @Id
    private String refundId;
    private String description;
    private Date refundDate;
    @ManyToOne
    @JoinColumn(name = "employeeCode")
    private EmployeeEntity employee;
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "inventoryCode", referencedColumnName = "inventoryCode"),
            @JoinColumn(name = "orderId", referencedColumnName = "orderId")
    })
    private SaleItemEntity saleItem;
}
