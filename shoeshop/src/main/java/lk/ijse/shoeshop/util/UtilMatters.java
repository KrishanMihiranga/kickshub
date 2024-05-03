package lk.ijse.shoeshop.util;

import lk.ijse.shoeshop.entity.enums.Gender;
import lk.ijse.shoeshop.entity.enums.Occasion;

import java.util.Base64;
import java.util.UUID;


public class UtilMatters {
    public static String generateId(){
        return UUID.randomUUID().toString();
    }
    public static String convertBase64(String data){
        return Base64.getEncoder().encodeToString(data.getBytes());
    }
    public static String generateItemId(long itemCount, Occasion occasion, Gender gender) {

        String itemNum = "000001";

        if (!(itemCount == 0)){
            long maxItemCode = itemCount;
            itemNum = String.format("%06d", maxItemCode + 1);
        }

        String[] occArr = {"FS", "CS", "IS", "SS"};
        String[] genArr = {"M", "F", "O"};

        String occId = null;
        String genId = null;


        switch (occasion) {
            case SPORT:
                occId = occArr[3];
                break;
            case INDUSTRIAL:
                occId = occArr[2];
                break;
            case CASUAL:
                occId = occArr[1];
                break;
            case FORMAL:
                occId = occArr[0];
                break;
        }
        switch (gender) {
            case MALE:
                genId = genArr[0];
                break;
            case FEMALE:
                genId = genArr[1];
                break;
            case OTHER:
                genId = genArr[2];
                break;
        }
        String itemID = occId + genId + itemNum;
        return itemID;
    }



}
