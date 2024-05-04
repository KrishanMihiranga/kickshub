package lk.ijse.shoeshop.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.shoeshop.dto.ItemDTO;
import lk.ijse.shoeshop.dto.ItemImageDTO;
import lk.ijse.shoeshop.dto.SaveItemDTO;
import lk.ijse.shoeshop.entity.ItemEntity;
import lk.ijse.shoeshop.entity.ItemImageEntity;
import lk.ijse.shoeshop.repo.ItemImageRepo;
import lk.ijse.shoeshop.repo.ItemRepo;
import lk.ijse.shoeshop.service.SaveItemService;
import lk.ijse.shoeshop.util.Mapping;
import lk.ijse.shoeshop.util.UtilMatters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class SaveItemServiceImpl implements SaveItemService {
    private final Mapping mapping;
    private final ItemRepo itemRepo;
    private final ItemImageRepo itemImageRepo;

    @Override
    public SaveItemDTO saveItem(SaveItemDTO saveItemDTO) {
        ItemDTO itemDTO = new ItemDTO();
        ItemImageDTO itemImageDTO = new ItemImageDTO();

        try {
            long itemCount = itemRepo.count();
            saveItemDTO.setItemCode(UtilMatters.generateItemId(itemCount, saveItemDTO.getOccasion(),saveItemDTO.getGender()));

            // Check if item code already exists
            if (itemRepo.existsByItemCode(saveItemDTO.getItemCode())) {
                throw new RuntimeException("Item code already exists: " + saveItemDTO.getItemCode());
            }

            // Set item attributes
            itemDTO.setItemCode(saveItemDTO.getItemCode());
            itemDTO.setDescription(saveItemDTO.getDescription());
            itemDTO.setCategory(saveItemDTO.getCategory());
            itemDTO.setSupplierName(saveItemDTO.getSupplierName());
            itemDTO.setSupplier(saveItemDTO.getSupplier());
            itemDTO.setUnitPriceSale(saveItemDTO.getUnitPriceSale());
            itemDTO.setUnitPriceBuy(saveItemDTO.getUnitPriceBuy());
            itemDTO.setExpectedPrice(saveItemDTO.getExpectedPrice());
            itemDTO.setProfitMargin(saveItemDTO.getProfitMargin());
            itemDTO.setOccasion(saveItemDTO.getOccasion());
            itemDTO.setGender(saveItemDTO.getGender());

            // Set item image attributes
            itemImageDTO.setId(saveItemDTO.getItemCode());
            itemImageDTO.setImage(saveItemDTO.getImage());

            // Save item and image entities
            ItemEntity savedItemEntity = itemRepo.save(mapping.toItemEntity(itemDTO));
            ItemImageEntity savedImageEntity = itemImageRepo.save(mapping.toItemImageEntity(itemImageDTO));

            // Map saved entities back to DTOs
            mapping.toItemDTO(savedItemEntity);
            mapping.toItemImageDTO(savedImageEntity);

            return saveItemDTO;
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while saving item: " + e.getMessage(), e);
        }
    }

    }

