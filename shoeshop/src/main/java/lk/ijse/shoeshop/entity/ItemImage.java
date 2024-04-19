package lk.ijse.shoeshop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "item-images")
public class ItemImage {
    @Id
    private String imageId;
    @Column(columnDefinition = "LONGTEXT")
    private String image;
}
