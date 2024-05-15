package lk.ijse.shoeshop.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.shoeshop.dto.ItemDTO;
import lk.ijse.shoeshop.entity.ItemEntity;
import lk.ijse.shoeshop.repo.ItemRepo;
import lk.ijse.shoeshop.service.ItemService;
import lk.ijse.shoeshop.util.Mapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {
    private final Mapping mapping;
    private final ItemRepo itemRepo;

    @Override
    public ItemDTO saveItem(ItemDTO itemDTO) {
        log.info("Saving item: {}", itemDTO);

        try {
            ItemEntity savedItemEntity = itemRepo.save(mapping.toItemEntity(itemDTO));
            log.debug("Item saved: {}", savedItemEntity);
            return mapping.toItemDTO(savedItemEntity);
        } catch (Exception e) {
            log.error("Failed to save item: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to save item: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ItemDTO> getAllItems() {
        log.info("Fetching all items");

        try {
            List<ItemEntity> allItems = itemRepo.findAll();
            log.debug("Fetched {} items", allItems.size());
            return mapping.getItemDTOList(allItems);
        } catch (Exception e) {
            log.error("Failed to fetch all items: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch all items: " + e.getMessage(), e);
        }
    }
}
