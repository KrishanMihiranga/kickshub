package lk.ijse.shoeshop.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.shoeshop.dto.SupplierDTO;
import lk.ijse.shoeshop.entity.SupplierEntity;
import lk.ijse.shoeshop.exception.NotFoundException;
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

    @Override
    public SupplierDTO updateSupplier(SupplierDTO supplierDTO){
        log.info("Updating Supplier: {}", supplierDTO);

        try {
            SupplierEntity existingSupplier = supplierRepo.findByCode(supplierDTO.getCode())
                    .orElseThrow(() -> new NotFoundException("Supplier not found"));

            // Update supplier details
            existingSupplier.setCode(existingSupplier.getCode());
            existingSupplier.setSupplierName(supplierDTO.getSupplierName());
            existingSupplier.setCategory(supplierDTO.getCategory());
            existingSupplier.setAddressNo(supplierDTO.getAddressNo());
            existingSupplier.setAddressLane(supplierDTO.getAddressLane());
            existingSupplier.setAddressCity(supplierDTO.getAddressCity());
            existingSupplier.setAddressState(supplierDTO.getAddressState());
            existingSupplier.setPostalCode(supplierDTO.getPostalCode());
            existingSupplier.setOriginCountry(supplierDTO.getOriginCountry());
            existingSupplier.setEmail(supplierDTO.getEmail());
            existingSupplier.setContactNo1(supplierDTO.getContactNo1());
            existingSupplier.setContactNo2(supplierDTO.getContactNo2());

            // Save and return updated supplier
            SupplierEntity updatedSupplier = supplierRepo.save(existingSupplier);
            log.debug("Supplier updated: {}", updatedSupplier);
            return mapping.toSupplierDTO(updatedSupplier);
        } catch (Exception e) {
            log.error("Failed to update Supplier: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to update supplier: " + e.getMessage(), e);
        }
    }
}
