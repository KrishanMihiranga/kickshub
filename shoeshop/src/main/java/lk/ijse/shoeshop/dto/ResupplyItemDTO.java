package lk.ijse.shoeshop.dto;

import jakarta.validation.constraints.NotNull;
import lk.ijse.shoeshop.entity.keys.ResupplyItemId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResupplyItemDTO implements SuperDTO{
    private ResupplyItemId resupplyItemId;
    @NotNull
    private Integer suppliedQty;
    @NotNull
    private Date suppliedDate;
}
