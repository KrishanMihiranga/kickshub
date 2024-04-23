package lk.ijse.shoeshop.repo;

import lk.ijse.shoeshop.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<EmployeeEntity, String> {
}
