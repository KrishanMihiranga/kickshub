package lk.ijse.shoeshop.service;

import lk.ijse.shoeshop.dto.ItemDTO;

import java.util.List;

public interface ItemService {
    ItemDTO saveItem(ItemDTO itemDTO);
    List<ItemDTO> getAllItems();

}
