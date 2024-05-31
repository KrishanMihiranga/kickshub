package lk.ijse.shoeshop.service;

import lk.ijse.shoeshop.dto.AlertDTO;

import java.util.List;

public interface AlertService {
    AlertDTO saveAlert(AlertDTO alertDTO);

    List<AlertDTO> getAllAlert();

    List<AlertDTO> getLatestAlert();
}
