package lk.ijse.gdse68.clothingposspring.dto;

import lk.ijse.gdse68.clothingposspring.customObj.ItemResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ItemDTO implements Serializable, ItemResponse {
    private String costume_id;
    private String type;
    private String color;
    private int amount;
    private String price;
    private String costume_picture;
}
