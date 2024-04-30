package lk.ijse.shoeshop.util;

import lk.ijse.shoeshop.entity.enums.ItemCategories;

import java.util.Base64;
import java.util.UUID;

public class UtilMatters {

    public static String generateId(){
        return UUID.randomUUID().toString();
    }
    public static String convertBase64(String data){
        return Base64.getEncoder().encodeToString(data.getBytes());
    }
    public static String generateItemId(ItemCategories category, String supplierName){
        return category.name()+"-"+supplierName;
    }
}
