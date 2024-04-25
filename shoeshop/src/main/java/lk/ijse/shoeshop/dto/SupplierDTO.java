package lk.ijse.shoeshop.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lk.ijse.shoeshop.entity.enums.SupplierCategories;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SupplierDTO implements SuperDTO{
    @Null(message = "Code is auto generated")
    private String code;
    @NotNull(message = "name cannot be null")
    private String supplierName;
    @NotNull(message = "category cannot be null")
    private SupplierCategories category;
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
    @NotNull(message = "origin cannot be null")
    private String originCountry;
    @NotNull(message = "Email cannot be null")
    private String email;
    @NotNull(message = "contact cannot be null")
    private String contactNo1;
    @NotNull(message = "landContact cannot be null")
    private String contactNo2;
}
