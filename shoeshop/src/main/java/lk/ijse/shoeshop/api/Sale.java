package lk.ijse.shoeshop.api;

import lk.ijse.shoeshop.dto.*;
import lk.ijse.shoeshop.service.SaleService;
import lk.ijse.shoeshop.service.SaveItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/sale")
@RequiredArgsConstructor
public class Sale {
    private final SaleService saleService;
    private final SaveItemService saveItemService;

    @GetMapping("/health")
    public String healthCheck(){
        return "Sale health check";
    }

    @GetMapping("/getall")
    public ResponseEntity<List<SaleDTO>> geAll(){
        return ResponseEntity.ok(saleService.getALl());
    }
    @PostMapping("/findorder")
    public ResponseEntity<SaleDTO> findOrder(@RequestBody String orderId){
        return ResponseEntity.ok(saleService.findSale(orderId));
    }
    @GetMapping("/getrecent")
    public ResponseEntity<List<SaleDTO>> getRecent(){
        return ResponseEntity.ok(saleService.latestOrders());
    }
    @PostMapping("/getitemname")
    public ResponseEntity<String> getName(@RequestBody String orderId){
        return ResponseEntity.ok(saleService.getProductName(orderId));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/savesale")
    public ResponseEntity<String> saveSale(@RequestBody SaleDTO saleDTO){
//        return ResponseEntity.ok(saleService.saveSale(saleDTO));
        try {
            saleService.saveSale(saleDTO);
            String successMessage = "Sale saved successfully!";
            return ResponseEntity.ok(successMessage);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = "Failed to save sale: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @PostMapping("/gettotalsales")
    public ResponseEntity<Double> getTotalSales(@RequestBody LocalDate date) {
        try {

            Double totalSales = saveItemService.getTotalValueOfItemsSoldWithin24Hours(date);


            return ResponseEntity.ok(totalSales);
        } catch (DateTimeParseException e) {
            e.printStackTrace();

            return ResponseEntity.badRequest().body(null);
        }
    }


    @PostMapping("/gettotalexpenses")
    public ResponseEntity<Double> getTotalExpenses(@RequestBody LocalDate date) {
        try {

            Double totalSales = saveItemService.getTotalValueOfItemsBuyWithin24hours(date);


            return ResponseEntity.ok(totalSales);
        } catch (DateTimeParseException e) {
            e.printStackTrace();

            return ResponseEntity.badRequest().body(null);
        }
    }


    @PostMapping("/gettotalincome")
    public ResponseEntity<Double> getTotalIncome(@RequestBody LocalDate date) {
        try {
            Double totalIncome = saveItemService.getTotalIncomeWithin24Hours(date);
            return ResponseEntity.ok(totalIncome);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/gettopproducts")
    public ResponseEntity<List<Top5DTO>> getTop5Products(@RequestBody LocalDate date){
        return ResponseEntity.ok(saveItemService.getTop5Products(date));
    }

    @PostMapping("/gettopselling")
    public ResponseEntity<TopSellingItemDTO> getTopSellingProduct(@RequestBody LocalDate date){
        return  ResponseEntity.ok(saveItemService.getTopSellingProduct(date));
    }
    @PostMapping("/gettotalpaymentmethods")
    public ResponseEntity<PaymentDetailsDTO> getTotalPaymentMethods(@RequestBody LocalDate date){
        return ResponseEntity.ok(saveItemService.totalPaymentMethods(date));
    }
}
