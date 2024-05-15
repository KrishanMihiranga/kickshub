package lk.ijse.shoeshop.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.shoeshop.dto.InventoryDTO;
import lk.ijse.shoeshop.entity.InventoryEntity;
import lk.ijse.shoeshop.repo.InventoryRepo;
import lk.ijse.shoeshop.service.InventoryService;
import lk.ijse.shoeshop.util.Mapping;
import lk.ijse.shoeshop.util.UtilMatters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepo inventoryRepo;
    private final Mapping mapping;

    @Override
    public InventoryDTO saveInventoryItem(InventoryDTO inventory) {

        log.info("Saving inventory item: {}", inventory);

        try {
            InventoryEntity existingInventory = inventoryRepo.findBySizeAndColorsAndItem_ItemCodeAndItemImage_Id(
                    inventory.getSize(), inventory.getColors(), inventory.getItem().getItemCode(), inventory.getItemImage().getId());

            if (existingInventory != null) {
                existingInventory.setOriginalQty(existingInventory.getOriginalQty() + inventory.getOriginalQty());
                existingInventory.setCurrentQty(existingInventory.getCurrentQty() + inventory.getOriginalQty());
                existingInventory.setStatus(inventory.getStatus());
                InventoryEntity savedInventoryEntity = inventoryRepo.save(existingInventory);
                log.debug("Existing inventory item updated: {}", savedInventoryEntity);
                return mapping.toInventoryDTO(savedInventoryEntity);
            } else {
                inventory.setInventoryCode(UtilMatters.generateId());
                InventoryEntity savedInventoryEntity = inventoryRepo.save(mapping.toInventoryEntity(inventory));
                log.debug("New inventory item saved: {}", savedInventoryEntity);
                return mapping.toInventoryDTO(savedInventoryEntity);
            }
        } catch (Exception e) {
            log.error("Failed to save inventory item: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to save inventory item: " + e.getMessage(), e);
        }
    }

    @Override
    public List<InventoryDTO> getAll() {
        log.info("Fetching all inventory items");

        try {
            List<InventoryEntity> allInventoryItems = inventoryRepo.findAll();
            log.debug("Fetched {} inventory items", allInventoryItems.size());
            return mapping.getInventoryDTOList(allInventoryItems);
        } catch (Exception e) {
            log.error("Failed to fetch all inventory items: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch all inventory items: " + e.getMessage(), e);
        }
    }
}
