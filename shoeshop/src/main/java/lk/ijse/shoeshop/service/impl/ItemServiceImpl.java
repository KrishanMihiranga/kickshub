package lk.ijse.shoeshop.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.shoeshop.dto.ItemDTO;
import lk.ijse.shoeshop.repo.ItemRepo;
import lk.ijse.shoeshop.service.ItemService;
import lk.ijse.shoeshop.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final Mapping mapping;
    private final ItemRepo itemRepo;

    @Override
    public ItemDTO saveItem(ItemDTO itemDTO) {
        return mapping.toItemDTO(itemRepo.save(mapping.toItemEntity(itemDTO)));
    }

    @Override
    public List<ItemDTO> getAllItems() {
        return mapping.getItemDTOList(itemRepo.findAll());
    }
}
