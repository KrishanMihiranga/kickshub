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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final Mapping mapping;
    private final EmployeeRepo employeeRepo;
    private final PasswordEncoder passwordEncoder;
    @Override
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        employeeDTO.setEmployeeCode(UtilMatters.generateId());
        employeeDTO.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
        return mapping.toEmployeeDTO(employeeRepo.save(mapping.toEmployeeEntity(employeeDTO)));
    }

    @Override
    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) {

        EmployeeEntity existingEmployee = employeeRepo.findByEmail(employeeDTO.getEmail())
                .orElseThrow(() -> new NotFoundException("Employee not found"));

//        existingEmployee.setEmployeeCode(employeeDTO.getEmployeeCode());
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
//        existingEmployee.setPassword(employeeDTO.getPassword());
        existingEmployee.setGuardianOrNominatedPerson(employeeDTO.getGuardianOrNominatedPerson());
        existingEmployee.setEmergencyContact(employeeDTO.getEmergencyContact());

        EmployeeEntity updatedEmployee = employeeRepo.save(existingEmployee);

        return mapping.toEmployeeDTO(updatedEmployee);
    }


    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return mapping.getEmployeeDTOList(employeeRepo.findAll());
    }

    @Override
    public Boolean check(CheckUserDTO user) {
        try {
            var userEntityOptional = employeeRepo.findByEmail(user.getEmail());
            if (userEntityOptional.isPresent()) {
                EmployeeEntity userEntity = userEntityOptional.get();
                String storedPassword = userEntity.getPassword();
                String enteredPassword = user.getPassword();

                boolean passwordMatches = passwordEncoder.matches(enteredPassword, storedPassword);
                return passwordMatches;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

}
