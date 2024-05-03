package lk.ijse.shoeshop.repo;

import lk.ijse.shoeshop.entity.RefundEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefundRepo extends JpaRepository<RefundEntity, String> {
}
