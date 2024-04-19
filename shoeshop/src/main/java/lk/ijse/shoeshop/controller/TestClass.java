package lk.ijse.shoeshop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestClass {
    @GetMapping
    public String healthCheck(){
        return "Hello kicksHub!";
    }
}
