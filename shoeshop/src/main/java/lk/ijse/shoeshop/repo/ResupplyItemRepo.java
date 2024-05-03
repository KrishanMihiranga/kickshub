package lk.ijse.shoeshop.repo;

import lk.ijse.shoeshop.entity.ResupplyItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResupplyItemRepo extends JpaRepository<ResupplyItemEntity, String> {
}
