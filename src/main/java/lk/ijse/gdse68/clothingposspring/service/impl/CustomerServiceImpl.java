package lk.ijse.gdse68.clothingposspring.service.impl;

import lk.ijse.gdse68.clothingposspring.customObj.CustomerErrorResponse;
import lk.ijse.gdse68.clothingposspring.customObj.CustomerResponse;
import lk.ijse.gdse68.clothingposspring.dao.CustomerDAO;
import lk.ijse.gdse68.clothingposspring.dto.CustomerDTO;
import lk.ijse.gdse68.clothingposspring.entity.Customer;
import lk.ijse.gdse68.clothingposspring.exception.CustomerNotFound;
import lk.ijse.gdse68.clothingposspring.exception.DataPersistFailedException;
import lk.ijse.gdse68.clothingposspring.service.CustomerService;
import lk.ijse.gdse68.clothingposspring.util.AppUtil;
import lk.ijse.gdse68.clothingposspring.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDAO customerDAO;
    @Autowired
    private Mapping mapping;


    @Override
    public void saveCustomer(CustomerDTO customerDTO) {
//        customerDTO.setCus_id(AppUtil.createCustomerID());
        var customer = mapping.convertToEntity(customerDTO);
        var savedCustomer = customerDAO.save(customer);

        if(savedCustomer == null){
            throw new DataPersistFailedException("Customer not saved");
        }
    }

    @Override
    public void updateCustomer(String cusId, CustomerDTO customerDTO) {
        Optional<Customer> tempCusEntity = customerDAO.findById(cusId);
        if (!tempCusEntity.isPresent()) {
            throw new CustomerNotFound(customerDTO.getCus_id());
        }
        else {
            tempCusEntity.get().setName(customerDTO.getName());
            tempCusEntity.get().setAddress(customerDTO.getAddress());
            tempCusEntity.get().setPhone(customerDTO.getPhone());
            tempCusEntity.get().setEmail(customerDTO.getEmail());
        }
    }

    @Override
    public void deleteCustomer(String cusId) {
        Optional<Customer> tempCusEntity = customerDAO.findById(cusId);
        if (!tempCusEntity.isPresent()) {
            throw new CustomerNotFound("Customer not found");
        }else {
            customerDAO.deleteById(cusId);
        }
    }

    @Override
    public List<CustomerDTO> getAllCustomer() {
        return mapping.convertToDTO(customerDAO.findAll());
    }

    @Override
    public CustomerResponse getSelectedCustomer(String cusId) {
        if(customerDAO.existsById(cusId)){
            return mapping.convertToDTO(customerDAO.getReferenceById(cusId));
        }else {
            return new CustomerErrorResponse(0, "Customer Not Found");
        }
    }
}
