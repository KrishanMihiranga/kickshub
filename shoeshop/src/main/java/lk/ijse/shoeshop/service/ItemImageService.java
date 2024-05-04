package lk.ijse.shoeshop.service;

import lk.ijse.shoeshop.dto.ItemImageDTO;

import java.util.List;

public interface ItemImageService {
    ItemImageDTO saveItemImage(ItemImageDTO itemImageDTO);
    List<ItemImageDTO> getAll();
}
