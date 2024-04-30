package lk.ijse.shoeshop.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import lk.ijse.shoeshop.dto.ItemDTO;
import lk.ijse.shoeshop.dto.SaveItemDTO;
import lk.ijse.shoeshop.entity.SupplierEntity;
import lk.ijse.shoeshop.entity.enums.ItemCategories;
import lk.ijse.shoeshop.service.SaveItemService;
import lk.ijse.shoeshop.util.UtilMatters;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin
@RestController
@RequestMapping("api/v1/svi")
@RequiredArgsConstructor
public class SaveItem {
    private final SaveItemService saveItemService;
    private final ObjectMapper objectMapper;
    @GetMapping("health")
    public String get(){
        return "Health Check ";
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping( value= "/saveimage")
    public ResponseEntity<SaveItemDTO> saveItem(@Valid @RequestPart("id")String id,
                                                @Valid @RequestPart("image")String image,
                                                @Valid @RequestPart("itemCode") String itemCode,
                                                @Valid @RequestPart("description") String description,
                                                @Valid @RequestPart("category") String category, // Convert enum to string
                                                @Valid @RequestPart("supplierName") String supplierName,
                                                @Valid @RequestPart("supplier") String supplierJson, // Serialize SupplierEntity to JSON
                                                @Valid @RequestPart("unitPriceSale") String unitPriceSale,
                                                @Valid @RequestPart("unitPriceBuy") String unitPriceBuy,
                                                @Valid @RequestPart("expectedPrice") String expectedPrice,
                                                @Valid @RequestPart("profitMargin") String profitMargin,
                                                    Errors errors) throws JsonProcessingException {
        System.out.println("Hits!");

        ItemCategories parsedCategory = ItemCategories.valueOf(category); // Convert string to enum

        SupplierEntity parsedSupplier = objectMapper.readValue(supplierJson, SupplierEntity.class); // Deserialize JSON to SupplierEntity


        if (errors.hasFieldErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    errors.getFieldErrors().get(0).getDefaultMessage());
        }

        //convert image to base64
        String base64ProPic = UtilMatters.convertBase64(image);

        //build Object
        SaveItemDTO saveItemDTO = new SaveItemDTO();

        saveItemDTO.setItemCode(itemCode);
        saveItemDTO.setDescription(description);
        saveItemDTO.setCategory(parsedCategory);
        saveItemDTO.setSupplierName(supplierName);
        saveItemDTO.setSupplier(parsedSupplier);
        saveItemDTO.setUnitPriceSale(Double.valueOf(unitPriceSale));
        saveItemDTO.setUnitPriceBuy(Double.valueOf(unitPriceBuy));
        saveItemDTO.setExpectedPrice(Double.valueOf(expectedPrice));
        saveItemDTO.setProfitMargin(Double.valueOf(profitMargin));

        saveItemDTO.setId(id);
        saveItemDTO.setImage(base64ProPic);


        return ResponseEntity.ok(saveItemService.saveItem(saveItemDTO));
    }
}
