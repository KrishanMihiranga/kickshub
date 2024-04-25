package lk.ijse.shoeshop.dto;

import jakarta.validation.constraints.Null;
import lk.ijse.shoeshop.entity.SupplierEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResupplyDTO implements SuperDTO{
    @Null(message = "ID is auto generated")
    private String supplyId;
    private Date suppliedDate;
    private Double totalAmount;
    private Integer totalQty;
    private SupplierEntity supplier;
}
