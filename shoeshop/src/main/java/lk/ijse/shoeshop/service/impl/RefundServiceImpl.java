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
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RefundServiceImpl implements RefundService {
    private final RefundRepo refundRepo;
    private final SaleRepo saleRepo;
    private final SaleItemRepo saleItemRepo;
    private final Mapping mapping;
    private final InventoryRepo inventoryRepo;

    @Override
    public RefundDTO saveRefund(RefundDTO refundDTO) {
        String inventoryCode = refundDTO.getSaleItem().getSaleItemId().getItem().getInventoryCode();
        String orderId = refundDTO.getSaleItem().getSaleItemId().getSale().getOrderId();

        InventoryEntity inventoryItem = inventoryRepo.findById(inventoryCode)
                .orElseThrow(() -> new IllegalArgumentException("Item not found for code: " + inventoryCode));

        Optional<RefundEntity> existingRefund = refundRepo.findByInventoryCodeAndOrderId(inventoryCode, orderId);
        if (existingRefund.isPresent()) {
            throw new AlreadyExistsException("Refund already exists for inventory code: " + inventoryCode + " and order id: " + orderId);
        }

        inventoryItem.setCurrentQty(inventoryItem.getCurrentQty() + refundDTO.getQty());


        RefundEntity refundEntity = mapping.toRefundEntity(refundDTO);
        refundEntity.setRefundId(UtilMatters.generateId());
        refundRepo.save(refundEntity);

        return mapping.toRefundDTO(refundEntity);
    }



    @Override
    public List<SaleDTO> getAvailableOrders() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, -3);
        Timestamp threeDaysAgo = new Timestamp(calendar.getTimeInMillis());


        List<SaleEntity> ordersWithinThreeDays = saleRepo.findAllByTimestampAfter(threeDaysAgo);

        return mapping.getSaleList(ordersWithinThreeDays);
    }



    @Override
    public List<RefundDetailsDTO> getRefundDetails(String orderId) {
        List<RefundDetailsDTO> refundDetailsList = new ArrayList<>();

        // Assuming you have repositories or services to fetch necessary data
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
                    }
                    refundDetailsDTO.setColor(inventory.getColors());
                    refundDetailsDTO.setItemCategory(item.getCategory());
                    refundDetailsDTO.setSize(inventory.getSize());
                    refundDetailsDTO.setQty(saleItem.getQty());
                    refundDetailsDTO.setDate(saleEntity.getTimestamp());
                    refundDetailsDTO.setImage(inventory.getItemImage().getImage());
                    refundDetailsDTO.setPrice(inventory.getItem().getUnitPriceSale());
                    refundDetailsDTO.setItemCode(inventory.getInventoryCode());
                    refundDetailsList.add(refundDetailsDTO);
                }
            }
        }

        return refundDetailsList;
    }


}
