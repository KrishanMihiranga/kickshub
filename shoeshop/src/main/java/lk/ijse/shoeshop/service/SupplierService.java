package lk.ijse.shoeshop.service;

import lk.ijse.shoeshop.dto.SupplierDTO;

import java.util.List;

public interface SupplierService {
    SupplierDTO saveSupplier(SupplierDTO supplierDTO);
    List<SupplierDTO> getAllSuppliers();
    SupplierDTO updateSupplier(SupplierDTO supplierDTO);
}
