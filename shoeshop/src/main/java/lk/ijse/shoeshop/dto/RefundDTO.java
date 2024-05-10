package lk.ijse.shoeshop.dto;

import jakarta.validation.constraints.Null;
import lk.ijse.shoeshop.entity.EmployeeEntity;
import lk.ijse.shoeshop.entity.SaleItemEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RefundDTO implements SuperDTO{
    @Null(message = "Id is auto generated")
    private String refundId;
    private String description;
    private Date refundDate;
    private EmployeeEntity employee;
    private SaleItemEntity saleItem;
    private int qty;
}
