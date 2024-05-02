package lk.ijse.shoeshop.service;

import lk.ijse.shoeshop.dto.EmployeeDTO;
import lk.ijse.shoeshop.dto.CheckUserDTO;

import java.util.List;

public interface EmployeeService {
    EmployeeDTO saveEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO updateEmployee(EmployeeDTO employeeDTO);
    List<EmployeeDTO> getAllEmployees();
    Boolean check(CheckUserDTO user);
}
