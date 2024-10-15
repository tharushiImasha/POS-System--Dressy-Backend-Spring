package lk.ijse.gdse68.clothingposspring.controller;

import lk.ijse.gdse68.clothingposspring.customObj.CustomerResponse;
import lk.ijse.gdse68.clothingposspring.dto.CustomerDTO;
import lk.ijse.gdse68.clothingposspring.exception.CustomerNotFound;
import lk.ijse.gdse68.clothingposspring.exception.DataPersistFailedException;
import lk.ijse.gdse68.clothingposspring.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private final CustomerService customerService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveCustomer(@RequestBody CustomerDTO customerDTO){
        logger.info("Received request to save customer: {}", customerDTO);
        if (customerDTO == null) {
            logger.warn("Received null customerDTO, returning BAD_REQUEST");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            try {
                customerService.saveCustomer(customerDTO);
                logger.info("Customer saved successfully: {}", customerDTO);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }catch (DataPersistFailedException e ){
                logger.error("Data persist failed for customer: {}", customerDTO, e);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                logger.error("Unexpected error occurred while saving customer: {}", customerDTO, e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CustomerDTO> getAllCustomers(){
        logger.info("Received request to get all customers");
        logger.info("All customers found");
        return customerService.getAllCustomer();
    }

    @GetMapping(value = "/{cus_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerResponse getSelectedCustomer(@PathVariable("cus_id") String id){
        logger.info("Received request to get selected customer: {}", id);
        return customerService.getSelectedCustomer(id);
    }

    @CrossOrigin(origins = "http://127.0.0.1:5501")
    @PatchMapping(value = "/{cus_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateCustomer(@PathVariable("cus_id") String id, @RequestBody CustomerDTO customerDTO){
        logger.info("Received request to update selected customer: {}", customerDTO);
        try {
            if (customerDTO == null && (id == null || id.isEmpty())) {
                logger.warn("Received null customerDTO or empty ID, returning BAD_REQUEST");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            customerService.updateCustomer(id, customerDTO);
            logger.info("Customer updated successfully: {}", customerDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (CustomerNotFound e ){
            logger.error("Customer not found for ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            logger.error("Unexpected error occurred while updating customer with ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping(value = "/{cus_id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("cus_id") String id){
        logger.info("Received request to delete selected customer: {}", id);
        try {
            customerService.deleteCustomer(id);
            logger.info("Customer deleted successfully: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (CustomerNotFound e ){
            logger.error("Customer not found for ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            logger.error("Unexpected error occurred while deleting customer with ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
