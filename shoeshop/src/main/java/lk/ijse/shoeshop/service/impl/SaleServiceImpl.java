package lk.ijse.shoeshop.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.shoeshop.dto.AlertDTO;
import lk.ijse.shoeshop.dto.SaleDTO;
import lk.ijse.shoeshop.entity.CustomerEntity;
import lk.ijse.shoeshop.entity.InventoryEntity;
import lk.ijse.shoeshop.entity.SaleEntity;
import lk.ijse.shoeshop.entity.SaleItemEntity;
import lk.ijse.shoeshop.entity.enums.CustomerLevel;
import lk.ijse.shoeshop.entity.enums.Gender;
import lk.ijse.shoeshop.repo.*;
import lk.ijse.shoeshop.service.AlertService;
import lk.ijse.shoeshop.service.SaleService;
import lk.ijse.shoeshop.util.Mapping;
import lk.ijse.shoeshop.util.UtilMatters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SaleServiceImpl implements SaleService {
    private final Mapping mapping;
    private final SaleRepo saleRepo;
    private final SaleItemRepo saleItemRepo;
    private final InventoryRepo inventoryRepo;
    private final CustomerRepo customerRepo;
    private final AlertService alertService;

    @Override
    @Transactional
    public SaleDTO saveSale(SaleDTO saleDTO) {
        log.info("Saving sale: {}", saleDTO);

        try {
            // Set order ID and calculate points
            saleDTO.setOrderId(UtilMatters.generateId());
            double totalPrice = saleDTO.getTotalPrice();
            int points = (int) (totalPrice / 800);
            saleDTO.setAddedPoints(points);

            // Save sale
            SaleEntity convertedEntity = mapping.toSaleEntity(saleDTO);
            convertedEntity.setTimestamp(Timestamp.from(Instant.now()));
            SaleEntity savedSaleEntity = saleRepo.save(convertedEntity);
            log.debug("Sale saved: {}", savedSaleEntity);

            // Update inventory
            updateInventory(savedSaleEntity);
            log.debug("Inventory updated for sale: {}", savedSaleEntity);

            // Update customer
            updateCustomer(saleDTO);
            log.debug("Customer updated for sale: {}", savedSaleEntity);

            // Save sale items
            for (SaleItemEntity saleItem : savedSaleEntity.getSaleItems()) {
                saleItem.getSaleItemId().setSale(savedSaleEntity);
                saleItemRepo.save(saleItem);
                log.debug("Sale item saved: {}", saleItem);
            }

            //Update alert
            updateAlert(saleDTO);
            log.debug("Alert updated for sale: {}", savedSaleEntity);

            log.info("Sale saved successfully.");
            return mapping.toSaleDTO(savedSaleEntity);
        } catch (Exception e) {
            log.error("Failed to save sale: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to save sale: " + e.getMessage(), e);
        }
    }

    @Transactional
    private void updateAlert(SaleDTO saleDTO) {
        AlertDTO alertDTO = new AlertDTO();

        String customerCode = saleDTO.getCustomer().getCustomerCode();
        Optional <CustomerEntity> customer = customerRepo.findByCustomerCode(customerCode);
        String customerName = customer.get().getName();
        alertDTO.setName(customerName);
        alertDTO.setEmpId(saleDTO.getEmployee().getEmployeeCode());

        String genderPronoun = customer.get().getGender().equals(Gender.MALE) ? "his" : "her";
        String productName = getProductName(saleDTO.getOrderId());

        String alertMessage = "received "+genderPronoun+" order of "+productName;
        alertDTO.setMessage(alertMessage);

        alertService.saveAlert(alertDTO);
    }

    @Override
    public List<SaleDTO> getALl() {
        log.info("Fetching all sales.");

        try {
            List<SaleEntity> allSales = saleRepo.findAll();
            log.debug("Total sales fetched: {}", allSales.size());
            return mapping.getSaleList(allSales);
        } catch (Exception e) {
            log.error("Failed to fetch all sales: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch all sales: " + e.getMessage(), e);
        }
    }

    @Override
    public SaleDTO findSale(String orderId) {
        log.info("Finding sale with order ID: {}", orderId);

        try {
            SaleEntity saleEntity = saleRepo.findById(orderId)
                    .orElseThrow(() -> new IllegalArgumentException("Sale not found for code: " + orderId));
            log.debug("Sale found: {}", saleEntity);
            return mapping.toSaleDTO(saleEntity);
        } catch (IllegalArgumentException e) {
            log.warn("Sale not found for order ID: {}", orderId);
            throw e;
        } catch (Exception e) {
            log.error("Failed to find sale: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to find sale: " + e.getMessage(), e);
        }
    }

    @Override
    public List<SaleDTO> latestOrders() {
        log.info("Fetching latest 5 orders.");

        try {
            Pageable pageable = PageRequest.of(0, 5);
            List<SaleEntity> saleEntities = saleRepo.findTop5ByOrderByTimestampDesc(pageable);
            log.debug("Latest orders fetched: {}", saleEntities);
            return mapping.toSaleDTOList(saleEntities);
        } catch (Exception e) {
            log.error("Failed to fetch latest orders: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch latest orders: " + e.getMessage(), e);
        }
    }

    @Override
    public String getProductName(String orderId) {
        log.info("Fetching product name for order ID: {}", orderId);

        try {
            StringBuilder items = new StringBuilder();
            List<SaleItemEntity> allBySaleItemIdOrderId = saleItemRepo.findAllBySaleItemIdSaleOrderId(orderId);

            for (SaleItemEntity saleItem : allBySaleItemIdOrderId) {
                InventoryEntity inventory = inventoryRepo.findById(saleItem.getSaleItemId().getItem().getInventoryCode())
                        .orElseThrow(() -> new IllegalArgumentException("Item not found for code: " + orderId));
                String tempItem = inventory.getItem().getDescription();
                if (items.length() == 0) {
                    items.append(tempItem);
                } else {
                    items.append(",").append(tempItem);
                }
            }

            String productName = items.toString();
            log.debug("Product name for order ID {}: {}", orderId, productName);
            return productName;
        } catch (IllegalArgumentException e) {
            log.warn("Item not found for order ID: {}", orderId);
            throw e;
        } catch (Exception e) {
            log.error("Failed to fetch product name: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch product name: " + e.getMessage(), e);
        }
    }

    @Transactional
    private void updateCustomer(SaleDTO saleDTO) {
        log.info("Updating customer for sale: {}", saleDTO);

        try {
            String customerCode = saleDTO.getCustomer().getCustomerCode();
            double totalPrice = saleDTO.getTotalPrice();
            int points = (int) (totalPrice / 800);

            CustomerEntity customerEntity = customerRepo.findById(customerCode)
                    .orElseThrow(() -> new IllegalArgumentException("Customer not found for code: " + customerCode));

            customerEntity.setTotalPoints(customerEntity.getTotalPoints() + points);
            int totalPoints = customerEntity.getTotalPoints();
            String level = determineCustomerLevel(totalPoints);
            customerEntity.setLevel(CustomerLevel.valueOf(level));

            customerEntity.setRecentPurchaseDateTime(Timestamp.from(Instant.now()));
            log.debug("Customer updated: {}", customerEntity);
        } catch (IllegalArgumentException e) {
            log.warn("Customer not found for sale: {}", saleDTO);
            throw e;
        } catch (Exception e) {
            log.error("Failed to update customer: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to update customer: " + e.getMessage(), e);
        }

    }

    @Transactional
    private void updateInventory(SaleEntity saleEntity) {
        log.info("Updating inventory for sale: {}", saleEntity);

        try {
            for (SaleItemEntity saleItem : saleEntity.getSaleItems()) {
                String invCode = saleItem.getSaleItemId().getItem().getInventoryCode();
                int quantity = saleItem.getQty();

                InventoryEntity inventoryEntity = inventoryRepo.findById(invCode)
                        .orElseThrow(() -> new IllegalArgumentException("Inventory not found for code: " + invCode));

                int updatedQty = inventoryEntity.getCurrentQty() - quantity;
                if (updatedQty < 0) {
                    throw new RuntimeException("Insufficient quantity in inventory for code: " + invCode);
                }

                inventoryEntity.setCurrentQty(updatedQty);
                inventoryRepo.save(inventoryEntity);
                log.debug("Inventory updated: {}", inventoryEntity);
            }
        } catch (IllegalArgumentException e) {
            log.warn("Inventory not found for sale: {}", saleEntity);
            throw e;
        } catch (RuntimeException e) {
            log.error("Failed to update inventory: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Failed to update inventory: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to update inventory: " + e.getMessage(), e);

        }
    }

    private String determineCustomerLevel(int points) {
        if (points >= 200) {
            return "GOLD";
        } else if (points >= 100) {
            return "SILVER";
        } else if (points >= 50) {
            return "BRONZE";
        } else {
            return "NEW";
        }
    }
}
