package lk.ijse.gdse68.clothingposspring.dto;

import lk.ijse.gdse68.clothingposspring.customObj.CustomerResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CustomerDTO implements Serializable, CustomerResponse {
    private String cus_id;
    private String name;
    private String email;
    private String address;
    private String phone;
}
