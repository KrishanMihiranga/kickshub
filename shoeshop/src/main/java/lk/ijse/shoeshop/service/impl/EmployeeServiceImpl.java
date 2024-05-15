package lk.ijse.shoeshop.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.shoeshop.dto.EmployeeDTO;
import lk.ijse.shoeshop.entity.EmployeeEntity;
import lk.ijse.shoeshop.exception.NotFoundException;
import lk.ijse.shoeshop.repo.EmployeeRepo;
import lk.ijse.shoeshop.dto.CheckUserDTO;
import lk.ijse.shoeshop.service.EmployeeService;
import lk.ijse.shoeshop.util.Mapping;
import lk.ijse.shoeshop.util.UtilMatters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    private final Mapping mapping;
    private final EmployeeRepo employeeRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        log.info("Saving employee: {}", employeeDTO);

        try {
            employeeDTO.setEmployeeCode(UtilMatters.generateId());
            employeeDTO.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
            EmployeeEntity savedEmployeeEntity = employeeRepo.save(mapping.toEmployeeEntity(employeeDTO));
            log.debug("Employee saved: {}", savedEmployeeEntity);
            return mapping.toEmployeeDTO(savedEmployeeEntity);
        } catch (Exception e) {
            log.error("Failed to save employee: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to save employee: " + e.getMessage(), e);
        }
    }

    @Override
    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) {

        log.info("Updating employee: {}", employeeDTO);

        try {
            EmployeeEntity existingEmployee = employeeRepo.findByEmail(employeeDTO.getEmail())
                    .orElseThrow(() -> new NotFoundException("Employee not found"));

            // Update employee details
            existingEmployee.setName(employeeDTO.getName());
            existingEmployee.setProfilePic(employeeDTO.getProfilePic());
            existingEmployee.setGender(employeeDTO.getGender());
            existingEmployee.setStatus(employeeDTO.getStatus());
            existingEmployee.setDesignation(employeeDTO.getDesignation());
            existingEmployee.setRole(employeeDTO.getRole());
            existingEmployee.setDob(employeeDTO.getDob());
            existingEmployee.setJoinedDate(employeeDTO.getJoinedDate());
            existingEmployee.setBranch(employeeDTO.getBranch());
            existingEmployee.setAddressNo(employeeDTO.getAddressNo());
            existingEmployee.setAddressLane(employeeDTO.getAddressLane());
            existingEmployee.setAddressCity(employeeDTO.getAddressCity());
            existingEmployee.setAddressState(employeeDTO.getAddressState());
            existingEmployee.setPostalCode(employeeDTO.getPostalCode());
            existingEmployee.setEmail(employeeDTO.getEmail());
            existingEmployee.setPhone(employeeDTO.getPhone());
            existingEmployee.setGuardianOrNominatedPerson(employeeDTO.getGuardianOrNominatedPerson());
            existingEmployee.setEmergencyContact(employeeDTO.getEmergencyContact());

            // Save and return updated employee
            EmployeeEntity updatedEmployee = employeeRepo.save(existingEmployee);
            log.debug("Employee updated: {}", updatedEmployee);
            return mapping.toEmployeeDTO(updatedEmployee);
        } catch (Exception e) {
            log.error("Failed to update employee: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to update employee: " + e.getMessage(), e);
        }
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        log.info("Fetching all employees");

        try {
            List<EmployeeEntity> allEmployees = employeeRepo.findAll();
            log.debug("Fetched {} employees", allEmployees.size());
            return mapping.getEmployeeDTOList(allEmployees);
        } catch (Exception e) {
            log.error("Failed to fetch all employees: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch all employees: " + e.getMessage(), e);
        }
    }

    @Override
    public Boolean check(CheckUserDTO user) {
        log.info("Checking user: {}", user.getEmail());

        try {
            var userEntityOptional = employeeRepo.findByEmail(user.getEmail());
            if (userEntityOptional.isPresent()) {
                EmployeeEntity userEntity = userEntityOptional.get();
                String storedPassword = userEntity.getPassword();
                String enteredPassword = user.getPassword();

                boolean passwordMatches = passwordEncoder.matches(enteredPassword, storedPassword);
                log.debug("Password match result: {}", passwordMatches);
                return passwordMatches;
            } else {
                log.debug("User not found");
                return false;
            }
        } catch (Exception e) {
            log.error("Error occurred while checking user: {}", e.getMessage(), e);
            return false;
        }
    }

}
