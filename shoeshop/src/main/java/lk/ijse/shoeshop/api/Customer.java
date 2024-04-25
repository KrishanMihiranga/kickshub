package lk.ijse.shoeshop.api;

import lk.ijse.shoeshop.dto.CustomerDTO;
import lk.ijse.shoeshop.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<CustomerDTO> saveCustomer(@RequestBody CustomerDTO customer){
        return ResponseEntity.ok(customerService.saveCustomer(customer));
    }

    @GetMapping(value = "/getAllCustomers",produces = "application/json")
    public ResponseEntity<List<CustomerDTO>> getALlCustomers(){
        List<CustomerDTO> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }
}
