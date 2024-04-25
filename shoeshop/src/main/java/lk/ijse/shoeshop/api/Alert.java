package lk.ijse.shoeshop.api;

import lk.ijse.shoeshop.dto.AlertDTO;
import lk.ijse.shoeshop.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1/alert")
@RequiredArgsConstructor
public class Alert {
    private final AlertService alertService;

    @GetMapping("/health")
    public String healthCheck(){
        return "Alert health check";
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/savealert")
    public ResponseEntity<AlertDTO> saveAlert(@RequestBody AlertDTO alertDTO){
        return ResponseEntity.ok(alertService.saveAlert(alertDTO));
    }
}
