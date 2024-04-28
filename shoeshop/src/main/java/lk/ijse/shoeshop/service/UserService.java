package lk.ijse.shoeshop.service;

import lk.ijse.shoeshop.dto.EmployeeDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDetailsService userDetailsService();
    void save(EmployeeDTO employeeDTO);
}
