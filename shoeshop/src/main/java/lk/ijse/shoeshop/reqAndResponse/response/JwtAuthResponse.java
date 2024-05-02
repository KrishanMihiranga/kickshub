package lk.ijse.shoeshop.reqAndResponse.response;

import lk.ijse.shoeshop.dto.ResponseEmployeeDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JwtAuthResponse {
    private String token;
    private ResponseEmployeeDTO employee;
}
