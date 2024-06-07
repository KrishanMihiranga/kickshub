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
public class ResponseEmployeeDTO implements SuperDTO{
        private String employeeCode;
        @NotNull
        private String name;
        @NotNull
        private String profilePic;
        @NotNull
        private Gender gender;
        @NotNull
        private String status;
        @NotNull
        private String designation;
        @NotNull
        private UserRole role;
        @NotNull
        private Date dob;
        private Date joinedDate;
        @NotNull
        private String branch;
        @NotNull
        private String addressNo;
        @NotNull
        private String addressLane;
        @NotNull
        private String addressCity;
        @NotNull
        private String addressState;
        @NotNull
        private String postalCode;
        @NotNull
        private String email;
        @NotNull
        private String phone;
        @NotNull
        private String guardianOrNominatedPerson;
        @NotNull
        private String emergencyContact;
}
