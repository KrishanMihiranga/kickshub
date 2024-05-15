package lk.ijse.shoeshop.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.shoeshop.dto.ItemImageDTO;
import lk.ijse.shoeshop.entity.ItemImageEntity;
import lk.ijse.shoeshop.repo.ItemImageRepo;
import lk.ijse.shoeshop.service.ItemImageService;
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
public class ItemImageServiceImpl implements ItemImageService {
    private final Mapping mapping;
    private final ItemImageRepo itemImageRepo;

    @Override
    public ItemImageDTO saveItemImage(ItemImageDTO itemImageDTO) {
        log.info("Saving item image: {}", itemImageDTO);

        try {
            itemImageDTO.setId(UtilMatters.generateId());
            ItemImageEntity savedItemImageEntity = itemImageRepo.save(mapping.toItemImageEntity(itemImageDTO));
            log.debug("Item image saved: {}", savedItemImageEntity);
            return mapping.toItemImageDTO(savedItemImageEntity);
        } catch (Exception e) {
            log.error("Failed to save item image: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to save item image: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ItemImageDTO> getAll() {
        log.info("Fetching all item images");

        try {
            List<ItemImageEntity> allItemImages = itemImageRepo.findAll();
            log.debug("Fetched {} item images", allItemImages.size());
            return mapping.getItemImageDTOList(allItemImages);
        } catch (Exception e) {
            log.error("Failed to fetch all item images: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch all item images: " + e.getMessage(), e);
        }
    }
}
