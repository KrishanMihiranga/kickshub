package lk.ijse.shoeshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "refund")
public class Refund {
   @Id
   private String refundId;
    private Date refundDate;
    private String description;
    @OneToOne
    private Employee employee;
}
