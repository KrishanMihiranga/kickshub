package lk.ijse.shoeshop.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lk.ijse.shoeshop.entity.enums.Gender;
import lk.ijse.shoeshop.entity.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmployeeDTO implements SuperDTO{
    @Null(message = "Id is auto generated")
    private String employeeCode;
    @NotNull(message = "Name cannot be null")
    private String name;
    @NotNull(message = "ProfilePic cannot be null")
    private String profilePic;
    @NotNull(message = "Gender cannot be null")
    private Gender gender;
    @NotNull(message = "status cannot be null")
    private String status;
    @NotNull(message = "designation cannot be null")
    private String designation;
    @NotNull(message = "Role cannot be null")
    private UserRole role;
    @NotNull(message = "dob cannot be null")
    private Date dob;
    @NotNull(message = "joinedDate cannot be null")
    private Date joinedDate;
    @NotNull(message = "branch cannot be null")
    private String branch;
    @NotNull(message = "addressNo cannot be null")
    private String addressNo;
    @NotNull(message = "addressLane cannot be null")
    private String addressLane;
    @NotNull(message = "addressCity cannot be null")
    private String addressCity;
    @NotNull(message = "addressState cannot be null")
    private String addressState;
    @NotNull(message = "postalCode cannot be null")
    private String postalCode;
    @NotNull(message = "email cannot be null")
    private String email;
    @NotNull(message = "phone cannot be null")
    private String phone;
    @NotNull(message = "password cannot be null")
    private String password;
    @NotNull(message = "guardianOrNominatedPerson cannot be null")
    private String guardianOrNominatedPerson;
    @NotNull(message = "emergencyContact cannot be null")
    private String emergencyContact;
}
