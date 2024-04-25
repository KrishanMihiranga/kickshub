package lk.ijse.shoeshop.dto;

import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AlertDTO implements SuperDTO{
    @Null(message = "ID Auto Generated")
    private String id;
    private String message;
    private Date date;
}
