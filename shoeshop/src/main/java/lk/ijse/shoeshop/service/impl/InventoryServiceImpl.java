package lk.ijse.shoeshop.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.shoeshop.dto.InventoryDTO;
import lk.ijse.shoeshop.repo.InventoryRepo;
import lk.ijse.shoeshop.service.InventoryService;
import lk.ijse.shoeshop.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepo inventoryRepo;
    private final Mapping mapping;

    @Override
    public InventoryDTO saveInventoryItem(InventoryDTO inventory) {
        return mapping.toInventoryDTO(inventoryRepo.save(mapping.toInventoryEntity(inventory)));
    }
}
