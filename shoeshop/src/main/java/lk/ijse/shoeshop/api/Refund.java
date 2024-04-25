package lk.ijse.shoeshop.api;

import lk.ijse.shoeshop.dto.RefundDTO;
import lk.ijse.shoeshop.service.RefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1/refund")
@RequiredArgsConstructor
public class Refund {
    private final RefundService refundService;

    @GetMapping("/health")
    public String healthCheck(){
        return "Refund health check";
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/saverefund")
    public ResponseEntity<String> saveRefund(@RequestBody RefundDTO refundDTO){
        //return ResponseEntity.ok(refundService.saveRefund(refundDTO));
        try {
            refundService.saveRefund(refundDTO);
            String successMessage = "Refund saved successfully!";
            return ResponseEntity.ok(successMessage);
        } catch (Exception e) {
            String errorMessage = "Failed to save Refund: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }
}
