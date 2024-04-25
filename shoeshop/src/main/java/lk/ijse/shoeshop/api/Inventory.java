package lk.ijse.shoeshop.api;

import lk.ijse.shoeshop.dto.InventoryDTO;
import lk.ijse.shoeshop.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1/inventory")
@RequiredArgsConstructor
public class Inventory {
    private final InventoryService inventoryService;


    @GetMapping("/health")
    public String healthCheck(){
        return "Inventory health check";
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/saveinventoryitem")
    public ResponseEntity<InventoryDTO> saveInventoryItem(@RequestBody InventoryDTO inventory){
        return ResponseEntity.ok(inventoryService.saveInventoryItem(inventory));
    }

}
