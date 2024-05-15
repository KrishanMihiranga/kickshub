package lk.ijse.shoeshop.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.shoeshop.dto.ResupplyItemDTO;
import lk.ijse.shoeshop.repo.ResupplyItemRepo;
import lk.ijse.shoeshop.service.ResupplyItemService;
import lk.ijse.shoeshop.util.Mapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ResupplyItemServiceImpl implements ResupplyItemService {
    private final Mapping mapping;
    private final ResupplyItemRepo resupplyItemRepo;

    @Override
    public ResupplyItemDTO saveResupplyItem(ResupplyItemDTO resupplyItemDTO) {
        return mapping.toResupplyItemDTO(resupplyItemRepo.save(mapping.toResupplyItemEntity(resupplyItemDTO)));
    }
}
