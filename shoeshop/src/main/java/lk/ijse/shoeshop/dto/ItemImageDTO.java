package lk.ijse.shoeshop.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemImageDTO implements SuperDTO{
    @Null(message = "Image Id is auto generated")
    private String id;
    @NotNull
    private String image;
}
