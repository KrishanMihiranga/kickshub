package lk.ijse.shoeshop.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lk.ijse.shoeshop.entity.enums.CustomerLevel;
import lk.ijse.shoeshop.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDTO implements SuperDTO{
    @Null(message = "Code is auto Generated")
    private String customerCode;
    @NotNull(message = "Name cannot be Null")
    private String name;
    @NotNull(message = "Gender cannot be Null")
    private Gender gender;
    @NotNull(message = "JoinedDate cannot be Null")
    private Date joinedDateAsLoyalty;
    @Null(message = "Level is Auto generated")
    private CustomerLevel level;
    @NotNull(message = "Points cannot be Null")
    private Integer totalPoints;
    @NotNull(message = "DOB cannot be Null")
    private Date dob;
    @NotNull(message = "addressNo cannot be Null")
    private String addressNo;
    @NotNull(message = "addressLane cannot be Null")
    private String addressLane;
    @NotNull(message = "addressCity cannot be Null")
    private String addressCity;
    @NotNull(message = "addressState cannot be Null")
    private String addressState;
    @NotNull(message = "postalCode cannot be Null")
    private String postalCode;
    @NotNull(message = "email cannot be Null")
    private String email;
    @NotNull(message = "phone cannot be Null")
    private String phone;
    @NotNull(message = "recentPurchaseDateTime cannot be Null")
    private Timestamp recentPurchaseDateTime;
}
