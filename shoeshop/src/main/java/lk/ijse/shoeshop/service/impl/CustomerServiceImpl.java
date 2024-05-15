package lk.ijse.shoeshop.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.shoeshop.dto.CheckCustomerDTO;
import lk.ijse.shoeshop.dto.CustomerDTO;
import lk.ijse.shoeshop.dto.PaymentDetailsDTO;
import lk.ijse.shoeshop.entity.CustomerEntity;
import lk.ijse.shoeshop.entity.SaleEntity;
import lk.ijse.shoeshop.entity.enums.CustomerLevel;
import lk.ijse.shoeshop.entity.enums.PaymentMethods;
import lk.ijse.shoeshop.repo.CustomerRepo;
import lk.ijse.shoeshop.service.CustomerService;
import lk.ijse.shoeshop.util.Mapping;
import lk.ijse.shoeshop.util.UtilMatters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final Mapping mapping;
    private final CustomerRepo customerRepo;

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("Saving customer: {}", customerDTO);

        try {
            customerDTO.setCustomerCode(UtilMatters.generateId());
            customerDTO.setLevel(CustomerLevel.NEW);
            CustomerEntity savedCustomerEntity = customerRepo.save(mapping.toCustomerEntity(customerDTO));
            log.debug("Customer saved: {}", savedCustomerEntity);
            return mapping.toCustomerDTO(savedCustomerEntity);
        } catch (Exception e) {
            log.error("Failed to save customer: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to save customer: " + e.getMessage(), e);
        }
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        log.info("Fetching all customers");

        try {
            List<CustomerEntity> allCustomers = customerRepo.findAll();
            log.debug("Fetched {} customers", allCustomers.size());
            return mapping.getCustomerDTOList(allCustomers);
        } catch (Exception e) {
            log.error("Failed to fetch all customers: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch all customers: " + e.getMessage(), e);
        }
    }

    @Override
    public Boolean check(CheckCustomerDTO customerDTO) {
        log.info("Checking customer: {}", customerDTO);

        try {
            Optional<CustomerEntity> existingCustomer = customerRepo.findByNameAndPhone(customerDTO.getName(), customerDTO.getContact());
            boolean exists = existingCustomer.isPresent();
            log.debug("Customer check result: {}", exists);
            return exists;
        } catch (Exception e) {
            log.error("Failed to check customer: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to check customer: " + e.getMessage(), e);
        }
    }
    @Override
    public int totalPaymentMethods(LocalDate date) {
        log.info("Calculating total payment methods for date: {}", date);

        try {
            LocalDateTime startDate = date.atStartOfDay();
            LocalDateTime endDate = date.atTime(23, 59, 59);

            // Retrieve customers within the specified date range
            List<CustomerEntity> customersWithinDateRange = customerRepo.findAllByjoinedDateAsLoyaltyBetween(startDate, endDate);

            int count = customersWithinDateRange.size();
            log.debug("Total payment methods for date {}: {}", date, count);
            return count;
        } catch (Exception e) {
            log.error("Failed to calculate total payment methods: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to calculate total payment methods: " + e.getMessage(), e);
        }
    }
}
