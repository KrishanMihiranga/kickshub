package lk.ijse.shoeshop.entity;

import jakarta.persistence.*;
import lk.ijse.shoeshop.entity.enums.SupplierCategories;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "supplier")
public class SupplierEntity {
    @Id
    private String code;
    private String supplierName;
    @Enumerated(EnumType.STRING)
    private SupplierCategories category;
    private String addressNo;
    private String addressLane;
    private String addressCity;
    private String addressState;
    private String postalCode;
    private String originCountry;
    @Column(unique = true)
    private String email;
    private String contactNo1;
    private String contactNo2;
}
