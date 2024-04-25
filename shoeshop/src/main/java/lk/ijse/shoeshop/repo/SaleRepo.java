package lk.ijse.shoeshop.repo;

import lk.ijse.shoeshop.entity.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepo extends JpaRepository<SaleEntity, String> {
}
