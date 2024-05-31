package lk.ijse.shoeshop.service;

import lk.ijse.shoeshop.dto.CheckCustomerDTO;
import lk.ijse.shoeshop.dto.CustomerDTO;

import java.time.LocalDate;
import java.util.List;

public interface CustomerService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    List<CustomerDTO> getAllCustomers();
    Boolean check(CheckCustomerDTO customerDTO);
    int totalPaymentMethods(LocalDate date);
    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    List<String> getMails();
}
