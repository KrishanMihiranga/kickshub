package lk.ijse.shoeshop.repo;

import lk.ijse.shoeshop.entity.SaleItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleItemRepo extends JpaRepository<SaleItemEntity, String> {
    List<SaleItemEntity> findAllBySaleItemIdSaleOrderId(String orderId);
}
