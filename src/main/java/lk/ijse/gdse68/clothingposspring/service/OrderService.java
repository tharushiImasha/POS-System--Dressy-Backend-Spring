package lk.ijse.gdse68.clothingposspring.service;

import lk.ijse.gdse68.clothingposspring.customObj.OrderResponse;
import lk.ijse.gdse68.clothingposspring.dto.OrdersDTO;

import java.util.List;

public interface OrderService {
    void saveOrder(OrdersDTO ordersDTO) ;
    List<OrdersDTO> getAllOrders() ;
    OrderResponse getSelectedOrder(String orderId) ;
}
