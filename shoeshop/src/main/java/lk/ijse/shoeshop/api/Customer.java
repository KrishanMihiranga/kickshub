package lk.ijse.shoeshop.api;

import jakarta.validation.Valid;
import lk.ijse.shoeshop.dto.CheckCustomerDTO;
import lk.ijse.shoeshop.dto.CustomerDTO;
import lk.ijse.shoeshop.service.CustomerService;
import lk.ijse.shoeshop.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/customer")
@RequiredArgsConstructor
public class Customer {
    private final CustomerService customerService;


    @GetMapping("/health")
    public String healthCheck(){
        return "customer health check";
    }


    @PostMapping
    public ResponseEntity<CustomerDTO> saveCustomer(@Valid @RequestBody CustomerDTO customer){

        return ResponseEntity.ok(customerService.saveCustomer(customer));
    }


    @GetMapping(value = "/getAllCustomers",produces = "application/json")
    public ResponseEntity<List<CustomerDTO>> getALlCustomers(){
        List<CustomerDTO> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @PostMapping("/check")
    public ResponseEntity<Boolean> check(@RequestBody CheckCustomerDTO customerDTO){
        return ResponseEntity.ok(customerService.check(customerDTO));
    }

    @PostMapping("/getcustomercount")
    public ResponseEntity<Integer> getCustomerCount(@RequestBody LocalDate date){
        return ResponseEntity.ok(customerService.totalPaymentMethods(date));
    }

    @PatchMapping("/updatecustomer")
    public ResponseEntity<CustomerDTO> updateCustomer(@Valid @RequestBody CustomerDTO customer){
        return ResponseEntity.ok(customerService.updateCustomer(customer));
    }

    @GetMapping("/getmails")
    public ResponseEntity<List<String>> getMails(){
        return ResponseEntity.ok(customerService.getMails());
    }
}
