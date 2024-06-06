package lk.ijse.shoeshop.api;

import lk.ijse.shoeshop.dto.SupplierDTO;
import lk.ijse.shoeshop.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/supplier")
@RequiredArgsConstructor
public class Supplier {
    private final SupplierService supplierService;


    @GetMapping("/health")
    public String healthCheck(){
        return "Supplier health check";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<SupplierDTO> saveSupplier(@RequestBody SupplierDTO supplier){
        return ResponseEntity.ok(supplierService.saveSupplier(supplier));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/updatesupplier")
    public ResponseEntity<SupplierDTO> updateSupplier(@RequestBody SupplierDTO supplier){
        return ResponseEntity.ok(supplierService.updateSupplier(supplier));
    }


    @GetMapping(value = "/getAllSuppliers",produces = "application/json")
    public ResponseEntity<List<SupplierDTO>> getALlSuppliers(){
        List<SupplierDTO> suppliers = supplierService.getAllSuppliers();
        return ResponseEntity.ok(suppliers);
    }
}
