package lk.ijse.shoeshop.repo;

import lk.ijse.shoeshop.entity.SaleEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SaleRepo extends JpaRepository<SaleEntity, String> {
    List<SaleEntity> findTop5ByOrderByTimestampDesc(Pageable pageable);
    List<SaleEntity> findAllByTimestampAfter(Timestamp timestamp);
    SaleEntity findByOrderId(String orderId);

    List<SaleEntity> findAllByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate);
}
