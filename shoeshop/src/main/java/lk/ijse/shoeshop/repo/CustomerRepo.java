package lk.ijse.shoeshop.repo;

import lk.ijse.shoeshop.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerEntity, String> {
    Optional<CustomerEntity> findByNameAndPhone(String name, String phone);

    List<CustomerEntity> findAllByjoinedDateAsLoyaltyBetween(LocalDateTime startDate, LocalDateTime endDate);

    Optional<CustomerEntity> findByCustomerCode(String customerCode);

    @Query(value = "SELECT * FROM Customer c WHERE MONTH(c.dob) = MONTH(CURRENT_DATE()) AND DAY(c.dob) = DAY(CURRENT_DATE())", nativeQuery = true)
    List<CustomerEntity> findCustomersWithTodayBirthday();

}
