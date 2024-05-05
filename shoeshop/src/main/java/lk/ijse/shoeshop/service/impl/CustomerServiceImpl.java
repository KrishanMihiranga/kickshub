package lk.ijse.shoeshop.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.shoeshop.dto.CheckCustomerDTO;
import lk.ijse.shoeshop.dto.CustomerDTO;
import lk.ijse.shoeshop.entity.CustomerEntity;
import lk.ijse.shoeshop.entity.enums.CustomerLevel;
import lk.ijse.shoeshop.repo.CustomerRepo;
import lk.ijse.shoeshop.service.CustomerService;
import lk.ijse.shoeshop.util.Mapping;
import lk.ijse.shoeshop.util.UtilMatters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final Mapping mapping;
    private final CustomerRepo customerRepo;
    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        customerDTO.setCustomerCode(UtilMatters.generateId());
        customerDTO.setLevel(CustomerLevel.NEW);
        return mapping.toCustomerDTO(customerRepo.save(mapping.toCustomerEntity(customerDTO)));
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return mapping.getCustomerDTOList(customerRepo.findAll());
    }

    @Override
    public Boolean check(CheckCustomerDTO customerDTO) {
        Optional<CustomerEntity> existingCustomer = customerRepo.findByNameAndPhone(customerDTO.getName(), customerDTO.getContact());
        return existingCustomer.isPresent();
    }
}
