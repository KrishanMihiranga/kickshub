package lk.ijse.shoeshop.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.shoeshop.dto.*;
import lk.ijse.shoeshop.entity.*;
import lk.ijse.shoeshop.entity.enums.PaymentMethods;
import lk.ijse.shoeshop.repo.InventoryRepo;
import lk.ijse.shoeshop.repo.ItemImageRepo;
import lk.ijse.shoeshop.repo.ItemRepo;
import lk.ijse.shoeshop.repo.SaleRepo;
import lk.ijse.shoeshop.service.SaveItemService;
import lk.ijse.shoeshop.util.Mapping;
import lk.ijse.shoeshop.util.UtilMatters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SaveItemServiceImpl implements SaveItemService {
    private final Mapping mapping;
    private final ItemRepo itemRepo;
    private final ItemImageRepo itemImageRepo;
    private final InventoryRepo inventoryRepo;
    private final SaleRepo saleRepo;
    @Override
    public SaveItemDTO saveItem(SaveItemDTO saveItemDTO) {
        ItemDTO itemDTO = new ItemDTO();
        ItemImageDTO itemImageDTO = new ItemImageDTO();

        try {
            log.info("Generating item code...");
            long itemCount = itemRepo.count();
            saveItemDTO.setItemCode(UtilMatters.generateItemId(itemCount, saveItemDTO.getOccasion(), saveItemDTO.getGender()));
            log.info("Generated item code: {}", saveItemDTO.getItemCode());

            // Check if item code already exists
            log.info("Checking if item code already exists...");
            if (itemRepo.existsByItemCode(saveItemDTO.getItemCode())) {
                throw new RuntimeException("Item code already exists: " + saveItemDTO.getItemCode());
            }

            // Set item attributes
            log.info("Setting item attributes...");
            itemDTO.setItemCode(saveItemDTO.getItemCode());
            itemDTO.setDescription(saveItemDTO.getDescription());
            itemDTO.setCategory(saveItemDTO.getCategory());
            itemDTO.setSupplierName(saveItemDTO.getSupplierName());
            itemDTO.setSupplier(saveItemDTO.getSupplier());
            itemDTO.setUnitPriceSale(saveItemDTO.getUnitPriceSale());
            itemDTO.setUnitPriceBuy(saveItemDTO.getUnitPriceBuy());
            itemDTO.setExpectedPrice(saveItemDTO.getExpectedPrice());
            itemDTO.setProfitMargin(saveItemDTO.getProfitMargin());
            itemDTO.setOccasion(saveItemDTO.getOccasion());
            itemDTO.setGender(saveItemDTO.getGender());

            // Set item image attributes
            log.info("Setting item image attributes...");
            itemImageDTO.setId(saveItemDTO.getItemCode());
            itemImageDTO.setImage(saveItemDTO.getImage());

            // Save item and image entities
            log.info("Saving item and image entities...");
            ItemEntity savedItemEntity = itemRepo.save(mapping.toItemEntity(itemDTO));
            ItemImageEntity savedImageEntity = itemImageRepo.save(mapping.toItemImageEntity(itemImageDTO));
            log.info("Item and image entities saved successfully.");

            // Map saved entities back to DTOs
            mapping.toItemDTO(savedItemEntity);
            mapping.toItemImageDTO(savedImageEntity);

            return saveItemDTO;
        } catch (Exception e) {
            log.error("Error occurred while saving item: {}", e.getMessage(), e);
            throw new RuntimeException("Error occurred while saving item: " + e.getMessage(), e);
        }
    }
    @Override
    public Double getTotalValueOfItemsSoldWithin24Hours(LocalDate date) {
        log.info("Calculating total value of items sold within 24 hours for date: {}", date);

        // Combine the provided date with time 00:00:00 to get the starting LocalDateTime
        LocalDateTime startDate = date.atStartOfDay();
        log.debug("Start date for the 24-hour period: {}", startDate);

        // Combine the provided date with time 23:59:59 to get the ending LocalDateTime
        LocalDateTime endDate = date.atTime(23, 59, 59);
        log.debug("End date for the 24-hour period: {}", endDate);

        // Retrieve orders within the specified date range
        List<SaleEntity> ordersWithinDateRange = saleRepo.findAllByTimestampBetween(startDate, endDate);

        // Calculate total value
        double totalValue = 0.0;
        for (SaleEntity sale : ordersWithinDateRange) {
            List<SaleItemEntity> saleItems = sale.getSaleItems();
            for (SaleItemEntity saleItem : saleItems) {
                double itemTotal = saleItem.getQty() * saleItem.getUnitPrice();
                totalValue += itemTotal;
                log.debug("Added item total: {} to the total value", itemTotal);
            }
        }
        log.info("Total value of items sold within 24 hours: {}", totalValue);
        return totalValue;

    }

    @Override
    public Double getTotalValueOfItemsBuyWithin24hours(LocalDate date){
        log.info("Calculating total value of items bought within 24 hours for date: {}", date);

        // Combine the provided date with time 00:00:00 to get the starting LocalDateTime
        LocalDateTime startDate = date.atStartOfDay();
        log.debug("Start date for the 24-hour period: {}", startDate);

        // Combine the provided date with time 23:59:59 to get the ending LocalDateTime
        LocalDateTime endDate = date.atTime(23, 59, 59);
        log.debug("End date for the 24-hour period: {}", endDate);

        List<SaleEntity> ordersWithinDateRange = saleRepo.findAllByTimestampBetween(startDate, endDate);

        double totalValue = 0.0;

        for (SaleEntity sale : ordersWithinDateRange) {
            List<SaleItemEntity> saleItems = sale.getSaleItems();
            for (SaleItemEntity saleItem : saleItems) {
                double itemTotal = saleItem.getQty() * saleItem.getSaleItemId().getItem().getItem().getUnitPriceBuy();
                totalValue += itemTotal;
                log.debug("Added item total: {} to the total value", itemTotal);
            }
        }
        log.info("Total value of items bought within 24 hours: {}", totalValue);
        return totalValue;
    }

    @Override
    public Double getTotalIncomeWithin24Hours(LocalDate date) {
        log.info("Calculating total income within 24 hours for date: {}", date);

        // Get total value of items sold within 24 hours
        Double totalValueSold = getTotalValueOfItemsSoldWithin24Hours(date);
        log.debug("Total value of items sold within 24 hours: {}", totalValueSold);

        // Get total value of items bought within 24 hours
        Double totalValueBought = getTotalValueOfItemsBuyWithin24hours(date);
        log.debug("Total value of items bought within 24 hours: {}", totalValueBought);

        // Calculate total income
        Double totalIncome = totalValueSold - totalValueBought;
        log.info("Total income within 24 hours: {}", totalIncome);

        return totalIncome;
    }

    @Override
    public List<Top5DTO> getTop5Products(LocalDate date) {
        log.info("Fetching top 5 products for date: {}", date);

        LocalDateTime startDate = date.atStartOfDay();
        LocalDateTime endDate = date.atTime(23, 59, 59);
        log.debug("Start date: {}, End date: {}", startDate, endDate);

        Map<String, Integer> productCountMap = new HashMap<>();
        Map<String, Integer> productQuantityMap = new HashMap<>();

        List<SaleEntity> ordersWithinDateRange = saleRepo.findAllByTimestampBetween(startDate, endDate);

        for (SaleEntity sale : ordersWithinDateRange) {
            List<SaleItemEntity> saleItems = sale.getSaleItems();
            for (SaleItemEntity saleItem : saleItems) {
                String inventoryCode = saleItem.getSaleItemId().getItem().getInventoryCode();
                int quantity = saleItem.getQty();
                productCountMap.put(inventoryCode, productCountMap.getOrDefault(inventoryCode, 0) + 1);
                productQuantityMap.put(inventoryCode, productQuantityMap.getOrDefault(inventoryCode, 0) + quantity);
            }
        }

        Map<String, Integer> totalCountMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : productQuantityMap.entrySet()) {
            String inventoryCode = entry.getKey();
            int count = entry.getValue();
            totalCountMap.put(inventoryCode, count);
        }

        List<Map.Entry<String, Integer>> sortedProducts = totalCountMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toList());

        List<Top5DTO> top5Products = new ArrayList<>();
        for (int i = 0; i < Math.min(5, sortedProducts.size()); i++) {
            String inventoryCode = sortedProducts.get(i).getKey();
            int totalCount = sortedProducts.get(i).getValue();

            Top5DTO top5DTO = new Top5DTO();
            top5DTO.setCount(totalCount);
            top5DTO.setName(inventoryRepo.findById(inventoryCode).get().getItem().getDescription());
            top5Products.add(top5DTO);
            log.debug("Added product to top 5: {}", top5DTO);
        }

        log.info("Top 5 products fetched successfully.");
        return top5Products;
    }

    @Override
    public TopSellingItemDTO getTopSellingProduct(LocalDate date){
        log.info("Fetching top selling product for date: {}", date);

        TopSellingItemDTO topSellingItemDTO = new TopSellingItemDTO();
        TopSellingItemDTO mostSoldProduct = null;

        LocalDateTime startDate = date.atStartOfDay();
        LocalDateTime endDate = date.atTime(23, 59, 59);
        log.debug("Start date: {}, End date: {}", startDate, endDate);

        Map<String, Integer> productQuantityMap = new HashMap<>();

        List<SaleEntity> ordersWithinDateRange = saleRepo.findAllByTimestampBetween(startDate, endDate);

        for (SaleEntity sale : ordersWithinDateRange) {
            List<SaleItemEntity> saleItems = sale.getSaleItems();
            for (SaleItemEntity saleItem : saleItems) {
                String inventoryCode = saleItem.getSaleItemId().getItem().getInventoryCode();
                int quantity = saleItem.getQty();
                productQuantityMap.put(inventoryCode, productQuantityMap.getOrDefault(inventoryCode, 0) + quantity);
            }
        }

        Map<String, Integer> totalCountMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : productQuantityMap.entrySet()) {
            String inventoryCode = entry.getKey();
            int count = entry.getValue();
            totalCountMap.put(inventoryCode, count);
        }

        List<Map.Entry<String, Integer>> sortedProducts = totalCountMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toList());

        List<TopSellingItemDTO> top5Products = new ArrayList<>();
        for (int i = 0; i < Math.min(5, sortedProducts.size()); i++) {
            String inventoryCode = sortedProducts.get(i).getKey();
            int totalCount = sortedProducts.get(i).getValue();

            TopSellingItemDTO tsdto = new TopSellingItemDTO();
            tsdto.setName(inventoryRepo.findById(inventoryCode).get().getItem().getDescription());
            tsdto.setPrice(inventoryRepo.findById(inventoryCode).get().getItem().getUnitPriceSale());
            tsdto.setImage(itemImageRepo.findById(inventoryRepo.findById(inventoryCode).get().getItem().getItemCode()).get().getImage());
            top5Products.add(tsdto);
        }

        if (!top5Products.isEmpty()) {
            mostSoldProduct = top5Products.get(0);
            log.debug("Top selling product: {}", mostSoldProduct);
        } else {
            log.warn("No sales recorded for the given date: {}", date);
        }

        return mostSoldProduct;
    }

    @Override
    public PaymentDetailsDTO totalPaymentMethods(LocalDate date) {
        log.info("Calculating total payment methods for date: {}", date);

        LocalDateTime startDate = date.atStartOfDay();
        LocalDateTime endDate = date.atTime(23, 59, 59);
        log.debug("Start date: {}, End date: {}", startDate, endDate);

        LocalDate previousDate = date.minusDays(1);
        LocalDateTime previousStartDate = previousDate.atStartOfDay();
        LocalDateTime previousEndDate = previousDate.atTime(23, 59, 59);
        log.debug("Previous start date: {}, Previous end date: {}", previousStartDate, previousEndDate);

        List<SaleEntity> ordersWithinDateRange = saleRepo.findAllByTimestampBetween(startDate, endDate);
        List<SaleEntity> ordersWithinPreviousDateRange = saleRepo.findAllByTimestampBetween(previousStartDate, previousEndDate);

        int cash = 0;
        int card = 0;
        for (SaleEntity sale : ordersWithinDateRange) {
            if (sale.getPaymentMethod().equals(PaymentMethods.CASH)) {
                cash++;
            } else {
                card++;
            }
        }
        log.debug("Cash payments for date: {}, Card payments for date: {}", cash, card);

        int previousCash = 0;
        int previousCard = 0;
        for (SaleEntity sale : ordersWithinPreviousDateRange) {
            if (sale.getPaymentMethod().equals(PaymentMethods.CASH)) {
                previousCash++;
            } else {
                previousCard++;
            }
        }
        log.debug("Cash payments for previous date: {}, Card payments for previous date: {}", previousCash, previousCard);

        double cashPercentageChange = calculatePercentageChange(previousCash, cash);
        double cardPercentageChange = calculatePercentageChange(previousCard, card);
        log.debug("Cash percentage change: {}, Card percentage change: {}", cashPercentageChange, cardPercentageChange);

        PaymentDetailsDTO paymentDetailsDTO = new PaymentDetailsDTO();
        paymentDetailsDTO.setCash(cash);
        paymentDetailsDTO.setCard(card);
        paymentDetailsDTO.setCashPercentageChange(cashPercentageChange);
        paymentDetailsDTO.setCardPercentageChange(cardPercentageChange);

        return paymentDetailsDTO;
    }

    // Method to calculate percentage change
    private double calculatePercentageChange(int previousValue, int currentValue) {
        if (previousValue == 0) {
            if (currentValue > 0)
                return 100; // Handle division by zero
            else
                return 0; // No change when both previous and current values are zero
        }

        double percentageChange;
        if (currentValue > previousValue) {
            percentageChange = ((double)(currentValue - previousValue) / previousValue) * 100;
        } else {
            percentageChange = -((double)(previousValue - currentValue) / previousValue) * 100;
        }
        return percentageChange;
    }



}

