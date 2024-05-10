package lk.ijse.shoeshop.service;

import lk.ijse.shoeshop.dto.RefundDTO;
import lk.ijse.shoeshop.dto.RefundDetailsDTO;
import lk.ijse.shoeshop.dto.SaleDTO;

import java.util.List;

public interface RefundService {
    RefundDTO saveRefund(RefundDTO refundDTO);
    List<SaleDTO> getAvailableOrders();
    List<RefundDetailsDTO> getRefundDetails(String orderId);
}
