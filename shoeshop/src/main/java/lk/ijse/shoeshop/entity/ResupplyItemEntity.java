package lk.ijse.shoeshop.entity;

import jakarta.persistence.*;
import lk.ijse.shoeshop.entity.keys.ResupplyItemId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "resupplyItem")
@AssociationOverrides({
        @AssociationOverride(name = "resupplyItemId.inventory", joinColumns = @JoinColumn(name = "inventoryCode")),
        @AssociationOverride(name = "resupplyItemId.resupply", joinColumns = @JoinColumn(name = "suppplyId"))
})
public class ResupplyItemEntity {
    @Id
    private ResupplyItemId resupplyItemId;
    private Integer suppliedQty;
    private Date suppliedDate;
}
