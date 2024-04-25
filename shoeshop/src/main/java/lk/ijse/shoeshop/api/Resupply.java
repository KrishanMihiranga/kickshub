package lk.ijse.shoeshop.api;

import lk.ijse.shoeshop.dto.ItemDTO;
import lk.ijse.shoeshop.dto.ResupplyDTO;
import lk.ijse.shoeshop.service.ResupplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/resupply")
@RequiredArgsConstructor
public class Resupply {
    private final ResupplyService resupplyService;

    @GetMapping("/health")
    public String healthCheck(){
        return "Resupply health check";
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/saveresupply")
    public ResponseEntity<ResupplyDTO> saveResupply(@RequestBody ResupplyDTO resupply){
        return ResponseEntity.ok(resupplyService.saveResupply(resupply));
    }
}
