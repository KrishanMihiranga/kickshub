package lk.ijse.shoeshop.service;

import lk.ijse.shoeshop.dto.InventoryDTO;

import java.util.List;

public interface InventoryService {
    InventoryDTO saveInventoryItem(InventoryDTO inventory);
    List<InventoryDTO> getAll();
}
