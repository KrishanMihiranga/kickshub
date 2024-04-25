package lk.ijse.shoeshop.repo;

import lk.ijse.shoeshop.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepo extends JpaRepository<ItemEntity, String> {
}
