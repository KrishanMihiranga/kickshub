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
   private double value;
    private Date refundDate;
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    private Employee employee;

    @OneToOne
    private SaleItems itemSale;
}
