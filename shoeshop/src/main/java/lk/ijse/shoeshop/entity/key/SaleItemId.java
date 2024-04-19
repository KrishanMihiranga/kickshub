package lk.ijse.shoeshop.entity.key;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lk.ijse.shoeshop.entity.Item;
import lk.ijse.shoeshop.entity.Sales;
import lombok.Data;

import java.io.Serializable;
@Embeddable
@Data
public class SaleItemId implements Serializable {
    @ManyToOne(cascade = CascadeType.ALL)
    private Sales sale;
    @ManyToOne(cascade = CascadeType.ALL)
    private Item item;
}
