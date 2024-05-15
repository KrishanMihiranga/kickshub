package lk.ijse.shoeshop.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.shoeshop.dto.RefundDTO;
import lk.ijse.shoeshop.dto.RefundDetailsDTO;
import lk.ijse.shoeshop.dto.SaleDTO;
import lk.ijse.shoeshop.entity.*;
import lk.ijse.shoeshop.exception.AlreadyExistsException;
import lk.ijse.shoeshop.repo.InventoryRepo;
import lk.ijse.shoeshop.repo.RefundRepo;
import lk.ijse.shoeshop.repo.SaleItemRepo;
import lk.ijse.shoeshop.repo.SaleRepo;
import lk.ijse.shoeshop.service.RefundService;
import lk.ijse.shoeshop.util.Mapping;
import lk.ijse.shoeshop.util.UtilMatters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RefundServiceImpl implements RefundService {
    private final RefundRepo refundRepo;
    private final SaleRepo saleRepo;
    private final SaleItemRepo saleItemRepo;
    private final Mapping mapping;
    private final InventoryRepo inventoryRepo;

    @Override
    public RefundDTO saveRefund(RefundDTO refundDTO) {
        log.info("Saving refund: {}", refundDTO);

        try {
            String inventoryCode = refundDTO.getSaleItem().getSaleItemId().getItem().getInventoryCode();
            String orderId = refundDTO.getSaleItem().getSaleItemId().getSale().getOrderId();

            InventoryEntity inventoryItem = inventoryRepo.findById(inventoryCode)
                    .orElseThrow(() -> new IllegalArgumentException("Item not found for code: " + inventoryCode));

            // Check if a refund already exists for the given inventory code and order ID
            refundRepo.findByInventoryCodeAndOrderId(inventoryCode, orderId)
                    .ifPresent(existingRefund -> {
                        throw new AlreadyExistsException("Refund already exists for inventory code: " + inventoryCode + " and order id: " + orderId);
                    });

            // Increase the current quantity of the inventory item
            inventoryItem.setCurrentQty(inventoryItem.getCurrentQty() + refundDTO.getQty());

            // Save the refund entity
            RefundEntity refundEntity = mapping.toRefundEntity(refundDTO);
            refundEntity.setRefundId(UtilMatters.generateId());
            refundRepo.save(refundEntity);

            log.debug("Refund saved: {}", refundEntity);
            return mapping.toRefundDTO(refundEntity);
        } catch (IllegalArgumentException e) {
            log.warn("Failed to save refund: {}", e.getMessage());
            throw e;
        } catch (AlreadyExistsException e) {
            log.warn("Failed to save refund: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Failed to save refund: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to save refund: " + e.getMessage(), e);
        }
    }

    @Override
    public List<SaleDTO> getAvailableOrders() {
        log.info("Fetching available orders");

        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_YEAR, -3);
            Timestamp threeDaysAgo = new Timestamp(calendar.getTimeInMillis());

            List<SaleEntity> ordersWithinThreeDays = saleRepo.findAllByTimestampAfter(threeDaysAgo);

            log.debug("Available orders fetched: {}", ordersWithinThreeDays.size());

            return mapping.getSaleList(ordersWithinThreeDays);
        } catch (Exception e) {
            log.error("Failed to fetch available orders: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch available orders: " + e.getMessage(), e);
        }
    }

    @Override
    public List<RefundDetailsDTO> getRefundDetails(String orderId) {
        log.info("Fetching refund details for order ID: {}", orderId);

        try {
            List<RefundDetailsDTO> refundDetailsList = new ArrayList<>();
            SaleEntity saleEntity = saleRepo.findByOrderId(orderId);

            if (saleEntity != null) {
                List<SaleItemEntity> saleItems = saleEntity.getSaleItems();
                for (SaleItemEntity saleItem : saleItems) {
                    RefundDetailsDTO refundDetailsDTO = new RefundDetailsDTO();
                    refundDetailsDTO.setOrderId(saleEntity.getOrderId());

                    // Fetching the item details
                    InventoryEntity inventory = saleItem.getSaleItemId().getItem();
                    if (inventory != null) {
                        ItemEntity item = inventory.getItem();
                        if (item != null) {
                            refundDetailsDTO.setItemName(item.getDescription());
                            refundDetailsDTO.setItemCategory(item.getCategory());
                            refundDetailsDTO.setPrice(item.getUnitPriceSale());
                        }
                        refundDetailsDTO.setColor(inventory.getColors());
                        refundDetailsDTO.setSize(inventory.getSize());
                        refundDetailsDTO.setQty(saleItem.getQty());
                        refundDetailsDTO.setDate(saleEntity.getTimestamp());
                        refundDetailsDTO.setImage(inventory.getItemImage().getImage());
                        refundDetailsDTO.setItemCode(inventory.getInventoryCode());
                        refundDetailsList.add(refundDetailsDTO);
                    }
                }
            }

            log.debug("Refund details fetched for order ID {}: {}", orderId, refundDetailsList.size());
            return refundDetailsList;
        } catch (Exception e) {
            log.error("Failed to fetch refund details for order ID {}: {}", orderId, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch refund details for order ID " + orderId + ": " + e.getMessage(), e);
        }
    }


}
