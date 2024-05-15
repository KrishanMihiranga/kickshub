package lk.ijse.shoeshop.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.shoeshop.dto.ResupplyDTO;
import lk.ijse.shoeshop.repo.ResupplyRepo;
import lk.ijse.shoeshop.service.ResupplyService;
import lk.ijse.shoeshop.util.Mapping;
import lk.ijse.shoeshop.util.UtilMatters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ResupplyServiceImpl implements ResupplyService {
    private final Mapping mapping;
    private final ResupplyRepo resupplyRepo;

    @Override
    public ResupplyDTO saveResupply(ResupplyDTO resupplyDTO) {
        resupplyDTO.setSupplyId(UtilMatters.generateId());
        return mapping.toResupplyDTO(resupplyRepo.save(mapping.toResupplyEntity(resupplyDTO)));
    }
}
