package lk.ijse.shoeshop.service;

import lk.ijse.shoeshop.dto.CheckCustomerDTO;
import lk.ijse.shoeshop.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    List<CustomerDTO> getAllCustomers();
    Boolean check(CheckCustomerDTO customerDTO);
}
