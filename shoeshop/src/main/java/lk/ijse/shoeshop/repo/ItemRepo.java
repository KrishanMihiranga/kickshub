package lk.ijse.shoeshop.repo;

import lk.ijse.shoeshop.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepo extends JpaRepository<ItemEntity, String> {
    boolean existsByItemCode(String itemCode);
}
