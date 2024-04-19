package lk.ijse.shoeshop.entity.key;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lk.ijse.shoeshop.entity.Item;
import lk.ijse.shoeshop.entity.SupplierHistory;
import lombok.Data;

import java.io.Serializable;
@Embeddable
@Data
public class SupplyItemId implements Serializable {
    @ManyToOne(cascade = CascadeType.ALL)
    private Item item;
    @ManyToOne(cascade = CascadeType.ALL)
    private SupplierHistory supplierHistory;
}
