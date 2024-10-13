package lk.ijse.gdse68.clothingposspring.customObj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemErrorResponse implements Serializable, ItemResponse {
    private int errorCode;
    private String errorMessage;
}
