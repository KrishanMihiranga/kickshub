package lk.ijse.shoeshop.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.shoeshop.dto.ItemImageDTO;
import lk.ijse.shoeshop.repo.ItemImageRepo;
import lk.ijse.shoeshop.service.ItemImageService;
import lk.ijse.shoeshop.util.Mapping;
import lk.ijse.shoeshop.util.UtilMatters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemImageServiceImpl implements ItemImageService {
    private final Mapping mapping;
    private final ItemImageRepo itemImageRepo;

    @Override
    public ItemImageDTO saveItemImage(ItemImageDTO itemImageDTO) {
        itemImageDTO.setId(UtilMatters.generateId());
        return mapping.toItemImageDTO(itemImageRepo.save(mapping.toItemImageEntity(itemImageDTO)));
    }
}
