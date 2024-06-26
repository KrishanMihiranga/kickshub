package lk.ijse.shoeshop.api;

import jakarta.validation.Valid;
import lk.ijse.shoeshop.dto.InventoryDTO;
import lk.ijse.shoeshop.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @GetMapping("/getall")
    public ResponseEntity<List<InventoryDTO>> getAll(){
        return ResponseEntity.ok(inventoryService.getAll());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/saveinventoryitem")
    public ResponseEntity<InventoryDTO> saveInventoryItem(@Valid  @RequestBody InventoryDTO inventory){
        return ResponseEntity.ok(inventoryService.saveInventoryItem(inventory));
    }

}
