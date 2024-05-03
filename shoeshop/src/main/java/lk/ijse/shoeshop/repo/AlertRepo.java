package lk.ijse.shoeshop.repo;

import lk.ijse.shoeshop.entity.AlertEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepo extends JpaRepository<AlertEntity, String> {
}
