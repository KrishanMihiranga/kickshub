package lk.ijse.shoeshop.dto;

import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private String description;

    private Date refundDate;
    @NotNull
    private EmployeeEntity employee;
    @NotNull
    private SaleItemEntity saleItem;
    @NotNull
    private int qty;
}
