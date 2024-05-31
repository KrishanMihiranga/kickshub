package lk.ijse.shoeshop.repo;

import lk.ijse.shoeshop.entity.AlertEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepo extends JpaRepository<AlertEntity, String> {
    List<AlertEntity> findTop3ByOrderByDateDesc();
}
