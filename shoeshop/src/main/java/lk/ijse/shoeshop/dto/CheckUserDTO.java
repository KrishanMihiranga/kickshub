package lk.ijse.shoeshop.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CheckUserDTO implements SuperDTO{
    @NotNull
    private String email;
    @NotNull
    private String password;
}
