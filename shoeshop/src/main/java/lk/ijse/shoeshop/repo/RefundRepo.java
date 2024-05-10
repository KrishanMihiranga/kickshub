package lk.ijse.shoeshop.repo;

import lk.ijse.shoeshop.entity.RefundEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefundRepo extends JpaRepository<RefundEntity, String> {
    @Query("SELECT r FROM RefundEntity r " +
            "WHERE r.saleItem.saleItemId.item.inventoryCode = :inventoryCode " +
            "AND r.saleItem.saleItemId.sale.orderId = :orderId")
    Optional<RefundEntity> findByInventoryCodeAndOrderId(@Param("inventoryCode") String inventoryCode, @Param("orderId") String orderId);
}
