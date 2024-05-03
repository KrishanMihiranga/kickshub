package lk.ijse.shoeshop.repo;

import lk.ijse.shoeshop.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepo extends JpaRepository<InventoryEntity, String> {
}
