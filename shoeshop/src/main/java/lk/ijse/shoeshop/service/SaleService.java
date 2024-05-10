package lk.ijse.shoeshop.service;

import lk.ijse.shoeshop.dto.SaleDTO;

import java.util.List;

public interface SaleService {
    SaleDTO saveSale(SaleDTO saleDTO);
    List<SaleDTO> getALl();
    SaleDTO findSale(String orderId);
    List<SaleDTO> latestOrders();
    String getProductName(String orderId);
}
