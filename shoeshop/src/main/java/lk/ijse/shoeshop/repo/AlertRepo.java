package lk.ijse.shoeshop.repo;

import lk.ijse.shoeshop.entity.AlertEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepo extends JpaRepository<AlertEntity, String> {
}
