package lk.ijse.shoeshop.api;

import jakarta.validation.Valid;
import lk.ijse.shoeshop.dto.ItemDTO;
import lk.ijse.shoeshop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/item")
@RequiredArgsConstructor
public class Item {
    private final ItemService itemService;


    @GetMapping("/health")
    public String healthCheck(){
        return "Item health check";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/saveitem")
    public ResponseEntity<ItemDTO> saveItem(@Valid  @RequestBody ItemDTO item){
        return ResponseEntity.ok(itemService.saveItem(item));
    }


    @GetMapping("/getall")
    public ResponseEntity<List<ItemDTO>> getAll(){
        return ResponseEntity.ok(itemService.getAllItems());
    }
}
