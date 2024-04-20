package lk.ijse.shoeshop.entity;

import jakarta.persistence.*;
import lk.ijse.shoeshop.entity.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "sales")
public class Sales {
    @Id
    private String orderId;
    private Integer itemQty;
    private Integer addedPoints;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    private Date orderDate;
    private Double totalPrice;

    @ManyToOne
    private Employee employee;
    @ManyToOne
    private Customer customer;
    @OneToMany(mappedBy = "saleItemId.sale", cascade = CascadeType.ALL)
    private List<SaleItems> saleItems;
}
