package lk.ijse.shoeshop.repo;

import lk.ijse.shoeshop.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<CustomerEntity, String> {
}
