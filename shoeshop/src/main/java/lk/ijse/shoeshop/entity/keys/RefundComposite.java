package lk.ijse.shoeshop.entity.keys;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class RefundComposite implements Serializable {
    private String saleItemInventoryCode;
    private String saleItemOrderId;
}
