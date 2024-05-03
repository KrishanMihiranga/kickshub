package lk.ijse.shoeshop.repo;

import lk.ijse.shoeshop.entity.ItemImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemImageRepo extends JpaRepository<ItemImageEntity, String> {
}
