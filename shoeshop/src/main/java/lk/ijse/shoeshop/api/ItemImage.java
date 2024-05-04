package lk.ijse.shoeshop.api;

import jakarta.validation.Valid;
import lk.ijse.shoeshop.dto.EmployeeDTO;
import lk.ijse.shoeshop.dto.ItemDTO;
import lk.ijse.shoeshop.dto.ItemImageDTO;
import lk.ijse.shoeshop.entity.enums.Gender;
import lk.ijse.shoeshop.entity.enums.UserRole;
import lk.ijse.shoeshop.service.ItemImageService;
import lk.ijse.shoeshop.util.Mapping;
import lk.ijse.shoeshop.util.UtilMatters;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/itemimage")
@RequiredArgsConstructor
public class ItemImage {
    private final ItemImageService itemImageService;

    @GetMapping("/health")
    public String healthCheck(){
        return "Item Image health check";
    }


    @GetMapping("/getall")
    public ResponseEntity<List<ItemImageDTO>> getAll(){
        return ResponseEntity.ok(itemImageService.getAll());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE , value= "/saveimage")
    public ResponseEntity<ItemImageDTO> saveItemImage(@Valid
                                                          @RequestPart("id")String id,
                                                          @RequestPart("image") MultipartFile image,
                                                          Errors errors){
        if (errors.hasFieldErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    errors.getFieldErrors().get(0).getDefaultMessage());
        }
        //convert image to base64
        byte[] byteImage = new byte[0];
        try {
            byteImage = image.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String base64ProPic = UtilMatters.convertBase64(byteImage);
        //build Object
       ItemImageDTO updatedBuildItemImage = new ItemImageDTO();
       updatedBuildItemImage.setId(id);
       updatedBuildItemImage.setImage(base64ProPic);
        return ResponseEntity.ok(itemImageService.saveItemImage(updatedBuildItemImage));
    }
}
