package lk.ijse.gdse68.clothingposspring.dto;

import lk.ijse.gdse68.clothingposspring.customObj.OrderResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OrdersDTO implements Serializable, OrderResponse {
    private String order_id;
    private String cus_id;
    private double total;
    private LocalDate date;
    private List<OrderDetailsDTO> order_details;

}
