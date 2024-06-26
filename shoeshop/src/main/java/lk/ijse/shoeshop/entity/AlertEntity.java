package lk.ijse.shoeshop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "alert")
public class AlertEntity {
    @Id
    private String id;
    private String empId;
    private String name;
    private String message;
    private Date date;
}
