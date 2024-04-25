package lk.ijse.shoeshop.util;

import lk.ijse.shoeshop.dto.*;
import lk.ijse.shoeshop.entity.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Mapping {
    @Autowired
    private final ModelMapper mapper;

    //Employee Mapping
    public EmployeeDTO toEmployeeDTO(EmployeeEntity employee){
        return mapper.map(employee, EmployeeDTO.class);
    }
    public EmployeeEntity toEmployeeEntity(EmployeeDTO employeeDTO){
        return mapper.map(employeeDTO, EmployeeEntity.class);
    }
    public List<EmployeeDTO> getEmployeeDTOList(List<EmployeeEntity> employeeEntities){
        return mapper.map(employeeEntities, List.class);
    }


    //Supplier Mapping
    public SupplierDTO toSupplierDTO(SupplierEntity supplier){
        return mapper.map(supplier, SupplierDTO.class);
    }
    public SupplierEntity toSupplierEntity(SupplierDTO supplierDTO){
        return mapper.map(supplierDTO, SupplierEntity.class);
    }
    public List<SupplierDTO> getSupplierDTOList(List<SupplierEntity> supplierEntities){
        return mapper.map(supplierEntities, List.class);
    }

    //Customer Mapping
    public CustomerDTO toCustomerDTO(CustomerEntity customer){
        return mapper.map(customer, CustomerDTO.class);
    }
    public CustomerEntity toCustomerEntity(CustomerDTO customerDTO){
        return mapper.map(customerDTO, CustomerEntity.class);
    }
    public List<CustomerDTO> getCustomerDTOList(List<CustomerEntity> customerEntities){
        return mapper.map(customerEntities, List.class);
    }

    //Item Mapping
    public ItemDTO toItemDTO(ItemEntity item){
        return mapper.map(item, ItemDTO.class);
    }
    public ItemEntity toItemEntity(ItemDTO itemDTO){
        return mapper.map(itemDTO, ItemEntity.class);
    }
    public List<ItemDTO> getItemDTOList(List<ItemEntity> itemEntities){
        return mapper.map(itemEntities, List.class);
    }

    //Inventory Mapping
    public InventoryDTO toInventoryDTO(InventoryEntity inventory){
        return mapper.map(inventory, InventoryDTO.class);
    }
    public InventoryEntity toInventoryEntity(InventoryDTO inventoryDTO){
        return mapper.map(inventoryDTO, InventoryEntity.class);
    }
    public List<InventoryDTO> getInventoryDTOList(List<InventoryEntity> inventoryEntities){
        return mapper.map(inventoryEntities, List.class);
    }

    //Item Image Mapping
    public ItemImageDTO toItemImageDTO(ItemImageEntity itemImage){
        return mapper.map(itemImage, ItemImageDTO.class);
    }
    public ItemImageEntity toItemImageEntity(ItemImageDTO itemImageDTO){
        return mapper.map(itemImageDTO, ItemImageEntity.class);
    }
    public List<ItemImageDTO> getItemImageDTOList(List<ItemImageEntity> itemImageEntities){
        return mapper.map(itemImageEntities, List.class);
    }

    //String to date
    public static Date convertToDate(String date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date updatedDate = null;
        try {
            updatedDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return updatedDate;
    }
}
