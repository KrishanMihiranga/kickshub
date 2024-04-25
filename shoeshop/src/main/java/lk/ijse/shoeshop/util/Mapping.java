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
    //Alert Mapping
    public AlertDTO toAlertDTO(AlertEntity alert){
        return mapper.map(alert, AlertDTO.class);
    }
    public AlertEntity toAlertEntity(AlertDTO alertDTO){
        return mapper.map(alertDTO, AlertEntity.class);
    }
    public List<AlertDTO> getAlertDTOList(List<AlertEntity> alertEntities){
        return mapper.map(alertEntities, List.class);
    }

    //Resupply Mapping
    public ResupplyDTO toResupplyDTO(ResupplyEntity resupply){
        return mapper.map(resupply, ResupplyDTO.class);
    }
    public ResupplyEntity toResupplyEntity(ResupplyDTO resupplyDTO){
        return mapper.map(resupplyDTO, ResupplyEntity.class);
    }
    public List<ResupplyDTO> getResupplyDTOList(List<ResupplyEntity> resupplyEntities){
        return mapper.map(resupplyEntities, List.class);
    }

    //Resupply Items Mapping
    public ResupplyItemDTO toResupplyItemDTO(ResupplyItemEntity resupply){
        return mapper.map(resupply, ResupplyItemDTO.class);
    }
    public ResupplyItemEntity toResupplyItemEntity(ResupplyItemDTO resupplyDTO){
        return mapper.map(resupplyDTO, ResupplyItemEntity.class);
    }
    public List<ResupplyItemDTO> getResupplyItemDTOList(List<ResupplyItemEntity> resupplyEntities){
        return mapper.map(resupplyEntities, List.class);
    }

    //Sale Mapping
    public SaleDTO toSaleDTO(SaleEntity saleEntity){
        return mapper.map(saleEntity, SaleDTO.class);
    }
    public SaleEntity toSaleEntity(SaleDTO saleDTO){
        return mapper.map(saleDTO, SaleEntity.class);
    }
    public List<SaleDTO> getSaleList(List<SaleEntity> saleEntities){
        return mapper.map(saleEntities, List.class);
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
