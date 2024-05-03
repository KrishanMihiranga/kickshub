package lk.ijse.shoeshop.repo;

import lk.ijse.shoeshop.entity.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepo extends JpaRepository<SaleEntity, String> {
}
