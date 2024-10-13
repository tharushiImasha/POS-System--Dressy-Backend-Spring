package lk.ijse.gdse68.clothingposspring.util;

import lk.ijse.gdse68.clothingposspring.dto.ItemDTO;

import java.util.Base64;
import java.util.UUID;

public class AppUtil {
    public static String createCustomerID() {
//        ItemDTO itemDTO = new ItemDTO();
//        String costumeId = itemDTO.getCostume_id();
//        if (costumeId == null || costumeId.isEmpty()) {
//            costumeId = "C"+001;
//        }else {
//            costumeId = costumeId.split("\\.")[0];
//        }
        return "Cus-"+UUID.randomUUID();
    }

   public static String createItemId(){
        return "ITEM-"+UUID.randomUUID();
   }

    public static String createOrderId(){
        return "ORDER-"+UUID.randomUUID();
    }

   public static String toBase64Pic(byte[] pic){
        return Base64.getEncoder().encodeToString(pic);
   }

}
