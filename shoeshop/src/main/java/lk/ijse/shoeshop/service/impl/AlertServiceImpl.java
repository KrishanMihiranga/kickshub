package lk.ijse.shoeshop.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.shoeshop.dto.AlertDTO;
import lk.ijse.shoeshop.repo.AlertRepo;
import lk.ijse.shoeshop.service.AlertService;
import lk.ijse.shoeshop.util.Mapping;
import lk.ijse.shoeshop.util.UtilMatters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
public class AlertServiceImpl implements AlertService {
    private final Mapping mapping;
    private final AlertRepo alertRepo;
    @Override
    public AlertDTO saveAlert(AlertDTO alertDTO) {
        alertDTO.setId(UtilMatters.generateId());
        alertDTO.setDate(new Date());
        return mapping.toAlertDTO(alertRepo.save(mapping.toAlertEntity(alertDTO)));
    }
}
