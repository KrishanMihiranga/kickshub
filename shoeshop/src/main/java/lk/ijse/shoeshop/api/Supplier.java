package lk.ijse.shoeshop.api;

import lk.ijse.shoeshop.dto.SupplierDTO;
import lk.ijse.shoeshop.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/supplier")
@RequiredArgsConstructor
public class Supplier {
    private final SupplierService supplierService;

    @GetMapping("/health")
    public String healthCheck(){
        return "Supplier health check";
    }

    @PostMapping
    public ResponseEntity<SupplierDTO> saveSupplier(@RequestBody SupplierDTO supplier){
        return ResponseEntity.ok(supplierService.saveSupplier(supplier));
    }
}
