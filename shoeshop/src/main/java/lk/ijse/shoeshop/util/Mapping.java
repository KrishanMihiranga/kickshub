package lk.ijse.shoeshop.util;

import lk.ijse.shoeshop.dto.EmployeeDTO;
import lk.ijse.shoeshop.dto.SupplierDTO;
import lk.ijse.shoeshop.entity.EmployeeEntity;
import lk.ijse.shoeshop.entity.SupplierEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class Mapping {
    private final ModelMapper mapper;

    //Employee Mapping
    public EmployeeDTO toEmployeeDTO(EmployeeEntity employee){
        return mapper.map(employee, EmployeeDTO.class);
    }
    public EmployeeEntity toEmployeeEntity(EmployeeDTO employeeDTO){
        return mapper.map(employeeDTO, EmployeeEntity.class);
    }


    //Supplier Mapping
    public SupplierDTO toSupplierDTO(SupplierEntity supplier){
        return mapper.map(supplier, SupplierDTO.class);
    }
    public SupplierEntity toSupplierEntity(SupplierDTO supplierDTO){
        return mapper.map(supplierDTO, SupplierEntity.class);
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
