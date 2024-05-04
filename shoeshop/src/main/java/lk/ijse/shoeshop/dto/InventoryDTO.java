package lk.ijse.shoeshop.dto;
import jakarta.validation.constraints.Null;
import lk.ijse.shoeshop.entity.ItemEntity;
import lk.ijse.shoeshop.entity.ItemImageEntity;
import lk.ijse.shoeshop.entity.enums.Colors;
import lk.ijse.shoeshop.entity.enums.Sizes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InventoryDTO implements SuperDTO{
    @Null
    private String inventoryCode;
    private Sizes size;
    private Colors colors;
    private Integer originalQty;
    private Integer currentQty;
    private String status;
    private ItemEntity item;
    private ItemImageEntity itemImage;
}
