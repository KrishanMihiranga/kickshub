package lk.ijse.shoeshop.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.shoeshop.dto.InventoryDTO;
import lk.ijse.shoeshop.entity.InventoryEntity;
import lk.ijse.shoeshop.repo.InventoryRepo;
import lk.ijse.shoeshop.service.InventoryService;
import lk.ijse.shoeshop.util.Mapping;
import lk.ijse.shoeshop.util.UtilMatters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepo inventoryRepo;
    private final Mapping mapping;

    @Override
    public InventoryDTO saveInventoryItem(InventoryDTO inventory) {

        InventoryEntity existingInventory = inventoryRepo.findBySizeAndColorsAndItem_ItemCodeAndItemImage_Id(inventory.getSize(), inventory.getColors(), inventory.getItem().getItemCode(), inventory.getItemImage().getId());

        if (existingInventory != null) {

            existingInventory.setOriginalQty(existingInventory.getOriginalQty()+inventory.getOriginalQty());
            existingInventory.setCurrentQty(existingInventory.getCurrentQty()+inventory.getOriginalQty());
            existingInventory.setStatus(inventory.getStatus());
            return mapping.toInventoryDTO(inventoryRepo.save(existingInventory));
        } else {

            inventory.setInventoryCode(UtilMatters.generateId());
            return mapping.toInventoryDTO(inventoryRepo.save(mapping.toInventoryEntity(inventory)));
        }
    }

    @Override
    public List<InventoryDTO> getAll() {
        return mapping.getInventoryDTOList(inventoryRepo.findAll());
    }
}
