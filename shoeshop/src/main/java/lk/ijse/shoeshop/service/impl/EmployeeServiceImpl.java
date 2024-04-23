package lk.ijse.shoeshop.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.shoeshop.dto.EmployeeDTO;
import lk.ijse.shoeshop.repo.EmployeeRepo;
import lk.ijse.shoeshop.service.EmployeeService;
import lk.ijse.shoeshop.util.Mapping;
import lk.ijse.shoeshop.util.UtilMatters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
