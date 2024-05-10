package lk.ijse.shoeshop.dto;

import lk.ijse.shoeshop.entity.enums.Colors;
import lk.ijse.shoeshop.entity.enums.ItemCategories;
import lk.ijse.shoeshop.entity.enums.Sizes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RefundDetailsDTO {
    private String orderId;
    private Colors color;
    private ItemCategories itemCategory;
    private String image;
    private Sizes size;
    private int qty;
    private Date date;
    private String itemName;
    private double price;
    private String itemCode;
}
