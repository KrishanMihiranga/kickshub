package lk.ijse.shoeshop.repo;

import lk.ijse.shoeshop.entity.CustomerEntity;
import lk.ijse.shoeshop.entity.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerEntity, String> {
    Optional<CustomerEntity> findByNameAndPhone(String name, String phone);

    List<CustomerEntity> findAllByjoinedDateAsLoyaltyBetween(LocalDateTime startDate, LocalDateTime endDate);

    Optional<CustomerEntity> findByCustomerCode(String customerCode);

}
