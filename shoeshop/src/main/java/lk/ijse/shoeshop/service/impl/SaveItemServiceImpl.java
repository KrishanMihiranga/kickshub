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
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
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
            long itemCount = itemRepo.count();
            saveItemDTO.setItemCode(UtilMatters.generateItemId(itemCount, saveItemDTO.getOccasion(),saveItemDTO.getGender()));

            // Check if item code already exists
            if (itemRepo.existsByItemCode(saveItemDTO.getItemCode())) {
                throw new RuntimeException("Item code already exists: " + saveItemDTO.getItemCode());
            }

            // Set item attributes
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
            itemImageDTO.setId(saveItemDTO.getItemCode());
            itemImageDTO.setImage(saveItemDTO.getImage());

            // Save item and image entities
            ItemEntity savedItemEntity = itemRepo.save(mapping.toItemEntity(itemDTO));
            ItemImageEntity savedImageEntity = itemImageRepo.save(mapping.toItemImageEntity(itemImageDTO));

            // Map saved entities back to DTOs
            mapping.toItemDTO(savedItemEntity);
            mapping.toItemImageDTO(savedImageEntity);

            return saveItemDTO;
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while saving item: " + e.getMessage(), e);
        }
    }

    @Override
    public Double getTotalValueOfItemsSoldWithin24Hours(LocalDate date) {
        // Combine the provided date with time 00:00:00 to get the starting LocalDateTime
        LocalDateTime startDate = date.atStartOfDay();
        System.out.println(startDate);

        // Combine the provided date with time 23:59:59 to get the ending LocalDateTime
        LocalDateTime endDate = date.atTime(23, 59, 59);
        System.out.println(endDate);

        // Retrieve orders within the specified date range
        List<SaleEntity> ordersWithinDateRange = saleRepo.findAllByTimestampBetween(startDate, endDate);

        // Calculate total value
        double totalValue = 0.0;
        for (SaleEntity sale : ordersWithinDateRange) {
            List<SaleItemEntity> saleItems = sale.getSaleItems();
            for (SaleItemEntity saleItem : saleItems) {
                totalValue += saleItem.getQty() * saleItem.getUnitPrice();
                System.out.println(totalValue+"******************");
            }
        }
        return totalValue;
    }

    @Override
    public Double getTotalValueOfItemsBuyWithin24hours(LocalDate date){
        LocalDateTime startDate = date.atStartOfDay();
        System.out.println(startDate);

        // Combine the provided date with time 23:59:59 to get the ending LocalDateTime
        LocalDateTime endDate = date.atTime(23, 59, 59);
        System.out.println(endDate);

        List<SaleEntity> ordersWithinDateRange = saleRepo.findAllByTimestampBetween(startDate, endDate);

        double totalValue = 0.0;

        for (SaleEntity sale: ordersWithinDateRange){
            List<SaleItemEntity> saleItems = sale.getSaleItems();
            for (SaleItemEntity saleItem: saleItems){
                totalValue+= saleItem.getQty() * saleItem.getSaleItemId().getItem().getItem().getUnitPriceBuy();
            }
        }
        return totalValue;
    }

    @Override
    public Double getTotalIncomeWithin24Hours(LocalDate date) {
        // Get total value of items sold within 24 hours
        Double totalValueSold = getTotalValueOfItemsSoldWithin24Hours(date);

        // Get total value of items bought within 24 hours
        Double totalValueBought = getTotalValueOfItemsBuyWithin24hours(date);

        // Calculate total income
        Double totalIncome = totalValueSold - totalValueBought;

        return totalIncome;
    }

    @Override
    public List<Top5DTO> getTop5Products(LocalDate date) {
        LocalDateTime startDate = date.atStartOfDay();
        LocalDateTime endDate = date.atTime(23, 59, 59);

        // Map to store inventory code and its occurrence count
        Map<String, Integer> productCountMap = new HashMap<>();
        Map<String, Integer> productQuantityMap = new HashMap<>();

        List<SaleEntity> ordersWithinDateRange = saleRepo.findAllByTimestampBetween(startDate, endDate);

        // Count occurrences of each product and their quantities
        for (SaleEntity sale : ordersWithinDateRange) {
            List<SaleItemEntity> saleItems = sale.getSaleItems();
            for (SaleItemEntity saleItem : saleItems) {
                String inventoryCode = saleItem.getSaleItemId().getItem().getInventoryCode();
                int quantity = saleItem.getQty();
                productCountMap.put(inventoryCode, productCountMap.getOrDefault(inventoryCode, 0) + 1);
                productQuantityMap.put(inventoryCode, productQuantityMap.getOrDefault(inventoryCode, 0) + quantity);
            }
        }

        // Calculate the total count as the sum of the count of occurrences of each product
        Map<String, Integer> totalCountMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : productQuantityMap.entrySet()) {
            String inventoryCode = entry.getKey();
            int count = entry.getValue();
            totalCountMap.put(inventoryCode, count);
        }

        // Sort the products based on the total count
        List<Map.Entry<String, Integer>> sortedProducts = totalCountMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toList());

        // Prepare Top5DTO objects for the top 5 products
        List<Top5DTO> top5Products = new ArrayList<>();
        for (int i = 0; i < Math.min(5, sortedProducts.size()); i++) {
            String inventoryCode = sortedProducts.get(i).getKey();
            int totalCount = sortedProducts.get(i).getValue();

            Top5DTO top5DTO = new Top5DTO();
            top5DTO.setCount(totalCount);
            top5DTO.setName(inventoryRepo.findById(inventoryCode).get().getItem().getDescription());
            top5Products.add(top5DTO);
            System.out.println("Inventory Code: " + inventoryCode + ", Total Count: " + totalCount);
        }

        return top5Products;
    }

    @Override
    public TopSellingItemDTO getTopSellingProduct(LocalDate date){
        TopSellingItemDTO topSellingItemDTO = new TopSellingItemDTO();
        TopSellingItemDTO mostSoldProduct = null;
        
        LocalDateTime startDate = date.atStartOfDay();
        LocalDateTime endDate = date.atTime(23, 59, 59);


        Map<String, Integer> productQuantityMap = new HashMap<>();

        List<SaleEntity> ordersWithinDateRange = saleRepo.findAllByTimestampBetween(startDate, endDate);

        // Count occurrences of each product and their quantities
        for (SaleEntity sale : ordersWithinDateRange) {
            List<SaleItemEntity> saleItems = sale.getSaleItems();
            for (SaleItemEntity saleItem : saleItems) {
                String inventoryCode = saleItem.getSaleItemId().getItem().getInventoryCode();
                int quantity = saleItem.getQty();
                productQuantityMap.put(inventoryCode, productQuantityMap.getOrDefault(inventoryCode, 0) + quantity);
            }
        }

        // Calculate the total count as the sum of the count of occurrences of each product
        Map<String, Integer> totalCountMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : productQuantityMap.entrySet()) {
            String inventoryCode = entry.getKey();
            int count = entry.getValue();
            totalCountMap.put(inventoryCode, count);
        }

        // Sort the products based on the total count
        List<Map.Entry<String, Integer>> sortedProducts = totalCountMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toList());

        // Prepare Top5DTO objects for the top 5 products
        List<TopSellingItemDTO> top5Products = new ArrayList<>();
        for (int i = 0; i < Math.min(5, sortedProducts.size()); i++) {
            String inventoryCode = sortedProducts.get(i).getKey();
            int totalCount = sortedProducts.get(i).getValue();

            TopSellingItemDTO tsdto= new TopSellingItemDTO();
            tsdto.setName(inventoryRepo.findById(inventoryCode).get().getItem().getDescription());
            tsdto.setPrice(inventoryRepo.findById(inventoryCode).get().getItem().getUnitPriceSale());
            tsdto.setImage(itemImageRepo.findById(inventoryRepo.findById(inventoryCode).get().getItem().getItemCode()).get().getImage());

            top5Products.add(tsdto);

        }
        if (!top5Products.isEmpty()) {
           
            mostSoldProduct = top5Products.get(0);
                
        } else {
            System.out.println("No sales recorded for the given date.");
        }
        return mostSoldProduct;
    }

    @Override
    public PaymentDetailsDTO totalPaymentMethods(LocalDate date) {
        LocalDateTime startDate = date.atStartOfDay();
        LocalDateTime endDate = date.atTime(23, 59, 59);

        // Calculate the previous day
        LocalDate previousDate = date.minusDays(1);
        LocalDateTime previousStartDate = previousDate.atStartOfDay();
        LocalDateTime previousEndDate = previousDate.atTime(23, 59, 59);

        // Retrieve orders within the specified date range and the previous day
        List<SaleEntity> ordersWithinDateRange = saleRepo.findAllByTimestampBetween(startDate, endDate);
        List<SaleEntity> ordersWithinPreviousDateRange = saleRepo.findAllByTimestampBetween(previousStartDate, previousEndDate);

        // Count payment methods for the specified date
        int cash = 0;
        int card = 0;
        for (SaleEntity sale : ordersWithinDateRange) {
            if (sale.getPaymentMethod().equals(PaymentMethods.CASH)) {
                cash++;
            } else {
                card++;
            }
        }

        // Count payment methods for the previous day
        int previousCash = 0;
        int previousCard = 0;
        for (SaleEntity sale : ordersWithinPreviousDateRange) {
            if (sale.getPaymentMethod().equals(PaymentMethods.CASH)) {
                previousCash++;
            } else {
                previousCard++;
            }
        }

        // Calculate the percentage change for cash and card payments
        double cashPercentageChange = calculatePercentageChange(previousCash, cash);
        double cardPercentageChange = calculatePercentageChange(previousCard, card);

        // Set the values in PaymentDetailsDTO
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

