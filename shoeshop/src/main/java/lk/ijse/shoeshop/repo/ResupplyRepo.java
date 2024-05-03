package lk.ijse.shoeshop.repo;

import lk.ijse.shoeshop.entity.ResupplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResupplyRepo extends JpaRepository<ResupplyEntity, String> {
}
