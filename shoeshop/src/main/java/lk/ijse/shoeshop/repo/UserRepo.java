package lk.ijse.shoeshop.repo;

import lk.ijse.shoeshop.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepo extends JpaRepository<EmployeeEntity, String> {
    Optional<EmployeeEntity> findByEmail(String email);
}
