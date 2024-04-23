package lk.ijse.shoeshop.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.shoeshop.dto.CustomerDTO;
import lk.ijse.shoeshop.repo.CustomerRepo;
import lk.ijse.shoeshop.service.CustomerService;
import lk.ijse.shoeshop.util.Mapping;
import lk.ijse.shoeshop.util.UtilMatters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final Mapping mapping;
    private final CustomerRepo customerRepo;
    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        customerDTO.setCustomerCode(UtilMatters.generateId());
        return mapping.toCustomerDTO(customerRepo.save(mapping.toCustomerEntity(customerDTO)));
    }
}
