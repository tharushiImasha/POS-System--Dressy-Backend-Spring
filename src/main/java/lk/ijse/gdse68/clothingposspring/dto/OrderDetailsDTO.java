package lk.ijse.gdse68.clothingposspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OrderDetailsDTO{
    private String order_detail_id;
    private String order_id;
    private String costume_id;
    private int quantity;
}
