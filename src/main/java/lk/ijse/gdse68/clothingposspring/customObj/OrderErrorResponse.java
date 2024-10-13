package lk.ijse.gdse68.clothingposspring.customObj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderErrorResponse implements Serializable, OrderResponse {
    private int errorCode;
    private String errorMessage;
}
