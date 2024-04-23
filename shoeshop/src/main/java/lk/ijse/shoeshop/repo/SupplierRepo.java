package lk.ijse.shoeshop.repo;

import lk.ijse.shoeshop.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepo extends JpaRepository<SupplierEntity, String> {
}
