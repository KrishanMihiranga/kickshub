package lk.ijse.shoeshop.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.shoeshop.dto.AlertDTO;
import lk.ijse.shoeshop.entity.AlertEntity;
import lk.ijse.shoeshop.repo.AlertRepo;
import lk.ijse.shoeshop.service.AlertService;
import lk.ijse.shoeshop.util.Mapping;
import lk.ijse.shoeshop.util.UtilMatters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AlertServiceImpl implements AlertService {
    private final Mapping mapping;
    private final AlertRepo alertRepo;

    @Override
    public AlertDTO saveAlert(AlertDTO alertDTO) {
        log.info("Saving alert: {}", alertDTO);

        try {
            alertDTO.setId(UtilMatters.generateId());
            alertDTO.setDate(new Date());
            AlertEntity savedAlertEntity = alertRepo.save(mapping.toAlertEntity(alertDTO));
            log.debug("Alert saved: {}", savedAlertEntity);
            return mapping.toAlertDTO(savedAlertEntity);
        } catch (Exception e) {
            log.error("Failed to save alert: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to save alert: " + e.getMessage(), e);
        }
    }
}
