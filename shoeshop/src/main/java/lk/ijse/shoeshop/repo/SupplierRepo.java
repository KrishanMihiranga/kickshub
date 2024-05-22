package lk.ijse.shoeshop.repo;

import lk.ijse.shoeshop.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepo extends JpaRepository<SupplierEntity, String> {
    Optional<SupplierEntity> findByCode(String code);
}
