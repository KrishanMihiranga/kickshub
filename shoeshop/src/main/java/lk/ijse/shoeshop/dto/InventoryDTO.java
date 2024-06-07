package lk.ijse.shoeshop.dto;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private Sizes size;
    @NotNull
    private Colors colors;
    @NotNull
    private Integer originalQty;
    @NotNull
    private Integer currentQty;
    @NotNull
    private String status;
    @NotNull
    private ItemEntity item;
    @NotNull
    private ItemImageEntity itemImage;
}
