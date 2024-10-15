package lk.ijse.gdse68.clothingposspring.controller;

import lk.ijse.gdse68.clothingposspring.customObj.OrderResponse;
import lk.ijse.gdse68.clothingposspring.dto.OrdersDTO;
import lk.ijse.gdse68.clothingposspring.exception.DataPersistFailedException;
import lk.ijse.gdse68.clothingposspring.service.OrderService;
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
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private final OrderService orderService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveOrder(@RequestBody OrdersDTO ordersDTO){
        if (ordersDTO == null) {
            logger.warn("Received null OrdersDTO for saving.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            try {
                orderService.saveOrder(ordersDTO);
                logger.info("Order saved successfully: {}", ordersDTO);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }catch (DataPersistFailedException e ){
                logger.error("Data persist failed for order: {}", ordersDTO, e);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                logger.error("Unexpected error occurred while saving order: {}", ordersDTO, e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OrdersDTO> getAllOrders(){
        logger.info("Received request to fetch all orders.");
        return orderService.getAllOrders();
    }

    @GetMapping(value = "/{order_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderResponse getSelectedOrder(@PathVariable("order_id") String id){
        logger.info("Received request to fetch selected order: {}", id);
        return orderService.getSelectedOrder(id);
    }

}
