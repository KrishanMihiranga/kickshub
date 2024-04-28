package lk.ijse.shoeshop.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.shoeshop.dto.EmployeeDTO;
import lk.ijse.shoeshop.entity.EmployeeEntity;
import lk.ijse.shoeshop.repo.EmployeeRepo;
import lk.ijse.shoeshop.service.EmployeeService;
import lk.ijse.shoeshop.util.Mapping;
import lk.ijse.shoeshop.util.UtilMatters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final Mapping mapping;
    private final EmployeeRepo employeeRepo;

    @Override
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        employeeDTO.setEmployeeCode(UtilMatters.generateId());
        return mapping.toEmployeeDTO(employeeRepo.save(mapping.toEmployeeEntity(employeeDTO)));
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return mapping.getEmployeeDTOList(employeeRepo.findAll());

    }
}
