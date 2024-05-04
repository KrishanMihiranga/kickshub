package lk.ijse.shoeshop.repo;

import lk.ijse.shoeshop.entity.InventoryEntity;
import lk.ijse.shoeshop.entity.enums.Colors;
import lk.ijse.shoeshop.entity.enums.Sizes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepo extends JpaRepository<InventoryEntity, String> {
    InventoryEntity findBySizeAndColorsAndItem_ItemCodeAndItemImage_Id(Sizes size, Colors colors, String itemCode, String id);
}
