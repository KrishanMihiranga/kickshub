package lk.ijse.shoeshop.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.shoeshop.dto.SupplierDTO;
import lk.ijse.shoeshop.entity.SupplierEntity;
import lk.ijse.shoeshop.repo.SupplierRepo;
import lk.ijse.shoeshop.service.SupplierService;
import lk.ijse.shoeshop.util.Mapping;
import lk.ijse.shoeshop.util.UtilMatters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SupplierServiceImpl implements SupplierService {
    private final Mapping mapping;
    private final SupplierRepo supplierRepo;

    @Override
    public SupplierDTO saveSupplier(SupplierDTO supplierDTO) {
        supplierDTO.setCode(UtilMatters.generateId());
        log.info("Saving Supplier with code: {}", supplierDTO.getCode());
        SupplierEntity savedEntity = supplierRepo.save(mapping.toSupplierEntity(supplierDTO));
        SupplierDTO savedDTO = mapping.toSupplierDTO(savedEntity);
        log.info("Supplier saved successfully with code: {}", savedDTO.getCode());
        return savedDTO;
    }

    @Override
    public List<SupplierDTO> getAllSuppliers() {
        log.info("Fetching all suppliers...");
        List<SupplierEntity> suppliers = supplierRepo.findAll();
        log.info("Fetched {} suppliers.", suppliers.size());
        return mapping.getSupplierDTOList(suppliers);
    }
}
