package lk.ijse.shoeshop.entity.keys;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lk.ijse.shoeshop.entity.InventoryEntity;
import lk.ijse.shoeshop.entity.SaleEntity;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class SaleItemId implements Serializable {
    @ManyToOne(cascade = CascadeType.ALL)
    private SaleEntity sale;
    @ManyToOne(cascade = CascadeType.ALL)
    private InventoryEntity item;
}
