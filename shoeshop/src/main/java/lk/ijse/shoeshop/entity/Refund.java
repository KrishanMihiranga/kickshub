package lk.ijse.shoeshop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
//@Entity
@Table(name = "refund")
public class Refund {
    private Date refundDate;
    private String description;
}
