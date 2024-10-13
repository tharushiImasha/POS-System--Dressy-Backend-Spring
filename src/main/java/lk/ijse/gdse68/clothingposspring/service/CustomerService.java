package lk.ijse.gdse68.clothingposspring.service;

import lk.ijse.gdse68.clothingposspring.customObj.CustomerResponse;
import lk.ijse.gdse68.clothingposspring.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    void saveCustomer(CustomerDTO customerDTO);
    void updateCustomer(String cusId, CustomerDTO customerDTO);
    void deleteCustomer(String cusId);
    List<CustomerDTO> getAllCustomer();
    CustomerResponse getSelectedCustomer(String cusId);
}
