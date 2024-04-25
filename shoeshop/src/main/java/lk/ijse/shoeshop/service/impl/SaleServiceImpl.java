package lk.ijse.shoeshop.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.shoeshop.dto.SaleDTO;
import lk.ijse.shoeshop.entity.SaleEntity;
import lk.ijse.shoeshop.entity.SaleItemEntity;
import lk.ijse.shoeshop.repo.SaleItemRepo;
import lk.ijse.shoeshop.repo.SaleRepo;
import lk.ijse.shoeshop.service.SaleService;
import lk.ijse.shoeshop.util.Mapping;
import lk.ijse.shoeshop.util.UtilMatters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {
    private final Mapping mapping;
    private final SaleRepo saleRepo;
    private final SaleItemRepo saleItemRepo;

    @Override
    public SaleDTO saveSale(SaleDTO saleDTO) {
        try{
            saleDTO.setOrderId(UtilMatters.generateId());
            SaleEntity savedSaleEntity = saleRepo.save(mapping.toSaleEntity(saleDTO));
            for (SaleItemEntity saleItem : savedSaleEntity.getSaleItems()){
                saleItem.getSaleItemId().setSale(savedSaleEntity);
                saleItemRepo.save(saleItem);
            }
            return mapping.toSaleDTO(savedSaleEntity);
        }catch (Exception e){
            throw new RuntimeException("Failed to save sale: "+ e.getMessage(), e);
        }
    }
}
