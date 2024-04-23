package lk.ijse.shoeshop.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.shoeshop.dto.SupplierDTO;
import lk.ijse.shoeshop.repo.SupplierRepo;
import lk.ijse.shoeshop.service.SupplierService;
import lk.ijse.shoeshop.util.Mapping;
import lk.ijse.shoeshop.util.UtilMatters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {
    private final Mapping mapping;
    private final SupplierRepo supplierRepo;

    @Override
    public SupplierDTO saveSupplier(SupplierDTO supplierDTO) {
        supplierDTO.setCode(UtilMatters.generateId());
        return mapping.toSupplierDTO(supplierRepo.save(mapping.toSupplierEntity(supplierDTO)));
    }
}
