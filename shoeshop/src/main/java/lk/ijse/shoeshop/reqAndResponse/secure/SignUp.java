package lk.ijse.shoeshop.reqAndResponse.secure;

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
public class SignUp {
    private String employeeCode;
    private String name;
    private String profilePic;
    private Gender gender;
    private String status;
    private String designation;
    private UserRole role;
    private Date dob;
    private Date joinedDate;
    private String branch;
    private String addressNo;
    private String addressLane;
    private String addressCity;
    private String addressState;
    private String postalCode;
    private String email;
    private String phone;
    private String password;
    private String guardianOrNominatedPerson;
    private String emergencyContact;
}
