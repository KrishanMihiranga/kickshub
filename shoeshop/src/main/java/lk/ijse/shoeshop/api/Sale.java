package lk.ijse.shoeshop.api;

import lk.ijse.shoeshop.dto.SaleDTO;
import lk.ijse.shoeshop.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1/sale")
@RequiredArgsConstructor
public class Sale {
    private final SaleService saleService;

    @GetMapping("/health")
    public String healthCheck(){
        return "Sale health check";
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/savesale")
    public ResponseEntity<String> saveSale(@RequestBody SaleDTO saleDTO){
//        return ResponseEntity.ok(saleService.saveSale(saleDTO));
        try {
            System.out.println(saleDTO);
            saleService.saveSale(saleDTO);
            String successMessage = "Sale saved successfully!";
            return ResponseEntity.ok(successMessage);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = "Failed to save sale: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }
}
