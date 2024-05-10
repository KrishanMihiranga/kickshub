package lk.ijse.shoeshop.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.shoeshop.dto.SaleDTO;
import lk.ijse.shoeshop.entity.CustomerEntity;
import lk.ijse.shoeshop.entity.InventoryEntity;
import lk.ijse.shoeshop.entity.SaleEntity;
import lk.ijse.shoeshop.entity.SaleItemEntity;
import lk.ijse.shoeshop.entity.enums.CustomerLevel;
import lk.ijse.shoeshop.repo.CustomerRepo;
import lk.ijse.shoeshop.repo.InventoryRepo;
import lk.ijse.shoeshop.repo.SaleItemRepo;
import lk.ijse.shoeshop.repo.SaleRepo;
import lk.ijse.shoeshop.service.SaleService;
import lk.ijse.shoeshop.util.Mapping;
import lk.ijse.shoeshop.util.UtilMatters;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {
    private final Mapping mapping;
    private final SaleRepo saleRepo;
    private final SaleItemRepo saleItemRepo;
    private final InventoryRepo inventoryRepo;
    private final CustomerRepo customerRepo;

    @Override
    @Transactional
    public SaleDTO saveSale(SaleDTO saleDTO) {
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

            // Update inventory
            updateInventory(savedSaleEntity);

            //update customer
            updateCustomer(saleDTO);

            // Save sale items
            for (SaleItemEntity saleItem : savedSaleEntity.getSaleItems()) {
                saleItem.getSaleItemId().setSale(savedSaleEntity);
                saleItemRepo.save(saleItem);
            }

            return mapping.toSaleDTO(savedSaleEntity);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save sale: " + e.getMessage(), e);
        }
    }

    @Override
    public List<SaleDTO> getALl() {
        return mapping.getSaleList(saleRepo.findAll());
    }

    @Override
    public SaleDTO findSale(String orderId) {
        SaleEntity saleEntity = saleRepo.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Sale not found for code: " + orderId));
        return mapping.toSaleDTO(saleEntity);
    }

    @Override
    public List<SaleDTO> latestOrders() {
        Pageable pageable = PageRequest.of(0, 5);
        List<SaleEntity> saleEntities = saleRepo.findTop5ByOrderByTimestampDesc(pageable);
        return mapping.toSaleDTOList(saleEntities);
    }

    @Override
    public String getProductName(String orderId) {
        String items = null;
        String tempItems;
        List<SaleItemEntity> allBySaleItemIdOrderId = saleItemRepo.findAllBySaleItemIdSaleOrderId(orderId);

        for (SaleItemEntity saleItem : allBySaleItemIdOrderId){
            InventoryEntity inventory = inventoryRepo.findById(saleItem.getSaleItemId().getItem().getInventoryCode())
                    .orElseThrow(() -> new IllegalArgumentException("Item not found for code: " + orderId));
            tempItems = inventory.getItem().getDescription();
            if (items == null){
                items = tempItems;
            }else {
                items = items+","+tempItems;
            }

        }
        return items;
    }


    @Transactional
    private void updateCustomer(SaleDTO saleDTO) {
        String customerCode = saleDTO.getCustomer().getCustomerCode();

        double totalPrice = saleDTO.getTotalPrice();
        int points = (int) (totalPrice / 800);

        CustomerEntity customerEntity = customerRepo.findById(customerCode)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found for code: " + customerCode));

        customerEntity.setTotalPoints(customerEntity.getTotalPoints()+points);
        int totalPoints = customerEntity.getTotalPoints();
        String level = determineCustomerLevel(totalPoints);
        customerEntity.setLevel(CustomerLevel.valueOf(level));

        customerEntity.setRecentPurchaseDateTime(Timestamp.from(Instant.now()));

    }

    @Transactional
    private void updateInventory(SaleEntity saleEntity) {
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
