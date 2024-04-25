package lk.ijse.shoeshop.api;

import lk.ijse.shoeshop.service.ResupplyItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/resupplyitem")
@RequiredArgsConstructor
public class ResupplyItem {
    private final ResupplyItemService resupplyItemService;

    @GetMapping("/health")
    public String healthCheck(){
        return "Resupply Item health check";
    }

}
