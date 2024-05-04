package lk.ijse.shoeshop.api;

import jakarta.validation.Valid;
import lk.ijse.shoeshop.dto.EmployeeDTO;
import lk.ijse.shoeshop.entity.enums.Gender;
import lk.ijse.shoeshop.entity.enums.UserRole;
import lk.ijse.shoeshop.dto.CheckUserDTO;
import lk.ijse.shoeshop.service.EmployeeService;
import lk.ijse.shoeshop.util.Mapping;
import lk.ijse.shoeshop.util.UtilMatters;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/employee")
@RequiredArgsConstructor
public class Employee {
    private final EmployeeService employeeService;

    @GetMapping("/health")
    public String healthCheck(){
        return "Employee Health Check";
    }

    @GetMapping("/user")
    public String helloUser(){
        return "Hello User";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String helloAdmin(){
        return "Hello admin";
    }

    //check valid user

    @PostMapping("/check")
    public ResponseEntity<Boolean> checkValidation(@RequestBody CheckUserDTO user){
        return ResponseEntity.ok(employeeService.check(user));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EmployeeDTO> saveEmployee(@Valid
                                    @RequestPart("profilePic") MultipartFile profilePic,
                                       @RequestPart("employeeCode")String employeeCode,
                                       @RequestPart("name")String name,
                                       @RequestPart("gender")String gender,
                                       @RequestPart("status")String status,
                                       @RequestPart("dob")String dob,
                                       @RequestPart("joinedDate")String joinedDate,
                                       @RequestPart("branch")String branch,
                                       @RequestPart("addressNo")String addressNo,
                                       @RequestPart("addressLane")String addressLane,
                                       @RequestPart("addressCity")String addressCity,
                                       @RequestPart("addressState")String addressState,
                                       @RequestPart("postalCode")String postalCode,
                                       @RequestPart("email")String email,
                                       @RequestPart("phone")String phone,
                                       @RequestPart("password")String password,
                                       @RequestPart("guardianOrNominatedPerson")String guardianOrNominatedPerson,
                                       @RequestPart("role")String role,
                                       @RequestPart("designation")String designation,
                                       @RequestPart("emergencyContact")String emergencyContact,
                                       Errors errors){
        if (errors.hasFieldErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    errors.getFieldErrors().get(0).getDefaultMessage());
        }
        //convert image to base64
        byte[] profilePicByte = new byte[0];
        try {
            profilePicByte = profilePic.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String base64ProPic = UtilMatters.convertBase64(profilePicByte);
        //build Object
        EmployeeDTO updatedBuildEmployee = new EmployeeDTO();
        updatedBuildEmployee.setEmployeeCode(employeeCode);
        updatedBuildEmployee.setName(name);
        updatedBuildEmployee.setGender(Gender.valueOf(gender));
        updatedBuildEmployee.setStatus(status);
        updatedBuildEmployee.setDob(Mapping.convertToDate(dob));
        updatedBuildEmployee.setJoinedDate(Mapping.convertToDate(joinedDate));
        updatedBuildEmployee.setBranch(branch);
        updatedBuildEmployee.setAddressNo(addressNo);
        updatedBuildEmployee.setAddressLane(addressLane);
        updatedBuildEmployee.setAddressCity(addressCity);
        updatedBuildEmployee.setAddressState(addressState);
        updatedBuildEmployee.setPostalCode(postalCode);
        updatedBuildEmployee.setEmail(email);
        updatedBuildEmployee.setPhone(phone);
        updatedBuildEmployee.setPassword(password);
        updatedBuildEmployee.setGuardianOrNominatedPerson(guardianOrNominatedPerson);
        updatedBuildEmployee.setEmergencyContact(emergencyContact);
        updatedBuildEmployee.setDesignation(designation);
        updatedBuildEmployee.setRole(UserRole.valueOf(role));
        updatedBuildEmployee.setProfilePic(base64ProPic);

        return ResponseEntity.ok(employeeService.saveEmployee(updatedBuildEmployee));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EmployeeDTO> updateEmployee(@Valid
                                                    @RequestPart("profilePic")MultipartFile profilePic,
                                                    @RequestPart("employeeCode")String employeeCode,
                                                    @RequestPart("name")String name,
                                                    @RequestPart("gender")String gender,
                                                    @RequestPart("status")String status,
                                                    @RequestPart("dob")String dob,
                                                    @RequestPart("joinedDate")String joinedDate,
                                                    @RequestPart("branch")String branch,
                                                    @RequestPart("addressNo")String addressNo,
                                                    @RequestPart("addressLane")String addressLane,
                                                    @RequestPart("addressCity")String addressCity,
                                                    @RequestPart("addressState")String addressState,
                                                    @RequestPart("postalCode")String postalCode,
                                                    @RequestPart("email")String email,
                                                    @RequestPart("phone")String phone,
                                                    @RequestPart("password")String password,
                                                    @RequestPart("guardianOrNominatedPerson")String guardianOrNominatedPerson,
                                                    @RequestPart("role")String role,
                                                    @RequestPart("designation")String designation,
                                                    @RequestPart("emergencyContact")String emergencyContact,
                                                    Errors errors){
        if (errors.hasFieldErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    errors.getFieldErrors().get(0).getDefaultMessage());
        }
        //convert image to base64
        byte[] profilePicByte = new byte[0];
        try {
            profilePicByte = profilePic.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String base64ProPic = UtilMatters.convertBase64(profilePicByte);
        //build Object
        EmployeeDTO updatedBuildEmployee = new EmployeeDTO();
        updatedBuildEmployee.setEmployeeCode(employeeCode);
        updatedBuildEmployee.setName(name);
        updatedBuildEmployee.setGender(Gender.valueOf(gender));
        updatedBuildEmployee.setStatus(status);
        updatedBuildEmployee.setDob(Mapping.convertToDate(dob));
        updatedBuildEmployee.setJoinedDate(Mapping.convertToDate(joinedDate));
        updatedBuildEmployee.setBranch(branch);
        updatedBuildEmployee.setAddressNo(addressNo);
        updatedBuildEmployee.setAddressLane(addressLane);
        updatedBuildEmployee.setAddressCity(addressCity);
        updatedBuildEmployee.setAddressState(addressState);
        updatedBuildEmployee.setPostalCode(postalCode);
        updatedBuildEmployee.setEmail(email);
        updatedBuildEmployee.setPhone(phone);
        updatedBuildEmployee.setPassword(password);
        updatedBuildEmployee.setGuardianOrNominatedPerson(guardianOrNominatedPerson);
        updatedBuildEmployee.setEmergencyContact(emergencyContact);
        updatedBuildEmployee.setDesignation(designation);
        updatedBuildEmployee.setRole(UserRole.valueOf(role));
        updatedBuildEmployee.setProfilePic(base64ProPic);

        return ResponseEntity.ok(employeeService.updateEmployee(updatedBuildEmployee));
    }




    @GetMapping(value = "/getAllEmployees",produces = "application/json")
    public ResponseEntity<List<EmployeeDTO>> getALlEmployees(){
        List<EmployeeDTO> employees = employeeService.getAllEmployees();

        return ResponseEntity.ok(employees);
    }
}
