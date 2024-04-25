package lk.ijse.shoeshop.repo;

import lk.ijse.shoeshop.entity.SaleItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleItemRepo extends JpaRepository<SaleItemEntity, String> {
}
