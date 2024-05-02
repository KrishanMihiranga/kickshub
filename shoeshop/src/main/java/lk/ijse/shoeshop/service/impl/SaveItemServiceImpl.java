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
            String code = UtilMatters.generateItemId(saveItemDTO.getCategory(), saveItemDTO.getSupplierName());

            itemDTO.setItemCode(code);
            itemDTO.setDescription(saveItemDTO.getDescription());
            itemDTO.setCategory(saveItemDTO.getCategory());
            itemDTO.setSupplierName(saveItemDTO.getSupplierName());
            itemDTO.setSupplier(saveItemDTO.getSupplier());
            itemDTO.setUnitPriceSale(saveItemDTO.getUnitPriceSale());
            itemDTO.setUnitPriceBuy(saveItemDTO.getUnitPriceBuy());
            itemDTO.setExpectedPrice(saveItemDTO.getExpectedPrice());
            itemDTO.setProfitMargin(saveItemDTO.getProfitMargin());

            itemImageDTO.setId(code);
            itemImageDTO.setImage(saveItemDTO.getImage());

            ItemEntity savedItemEntity = itemRepo.save(mapping.toItemEntity(itemDTO));
            ItemImageEntity savedImageEntity = itemImageRepo.save(mapping.toItemImageEntity(itemImageDTO));

            mapping.toItemDTO(savedItemEntity);
            mapping.toItemImageDTO(savedImageEntity);
            return saveItemDTO;
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while saving item: " + e.getMessage(), e);
        }
    }
}

