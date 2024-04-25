package lk.ijse.shoeshop.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemImageDTO implements SuperDTO{
    @NotNull(message = "Image Id is auto generated")
    private String id;
    private String image;
}
