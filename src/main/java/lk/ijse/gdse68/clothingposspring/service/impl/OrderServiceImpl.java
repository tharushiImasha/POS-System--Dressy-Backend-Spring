package lk.ijse.gdse68.clothingposspring.service.impl;

import lk.ijse.gdse68.clothingposspring.customObj.OrderErrorResponse;
import lk.ijse.gdse68.clothingposspring.customObj.OrderResponse;
import lk.ijse.gdse68.clothingposspring.dao.ItemDAO;
import lk.ijse.gdse68.clothingposspring.dao.OrderDAO;
import lk.ijse.gdse68.clothingposspring.dao.OrderDetailsDAO;
import lk.ijse.gdse68.clothingposspring.dto.OrderDetailsDTO;
import lk.ijse.gdse68.clothingposspring.dto.OrdersDTO;
import lk.ijse.gdse68.clothingposspring.entity.Item;
import lk.ijse.gdse68.clothingposspring.entity.OrderDetails;
import lk.ijse.gdse68.clothingposspring.exception.DataPersistFailedException;
import lk.ijse.gdse68.clothingposspring.service.OrderService;
import lk.ijse.gdse68.clothingposspring.util.AppUtil;
import lk.ijse.gdse68.clothingposspring.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private ItemDAO itemDAO;
    @Autowired
    private OrderDetailsDAO orderDetailsDAO;
    @Autowired
    private Mapping mapping;

    @Override
    @Transactional
    public void saveOrder(OrdersDTO ordersDTO) {
        System.out.println(ordersDTO);

        ordersDTO.setOrder_id(AppUtil.createOrderId()); // Ensure this is setting a valid order ID

        var order = mapping.convertToOrderEntity(ordersDTO);
        var savedOrder = orderDAO.save(order); // Saving the order first
        System.out.println(order);

        if (savedOrder != null) {
            List<OrderDetailsDTO> orderDetails = ordersDTO.getOrder_details();
            for (OrderDetailsDTO orderDetail : orderDetails) {

                Item item = itemDAO.findById(orderDetail.getCostume_id())
                        .orElseThrow(() -> new DataPersistFailedException("Item not found"));

                // Save the OrderDetails with the fetched Order and Item entities
                OrderDetails newOrderDetail = new OrderDetails(
                        orderDetail.getOrder_detail_id(),
                        order,  // Pass the Orders object
                        item,    // Pass the Item object
                        orderDetail.getQuantity()
                );
                orderDetailsDAO.save(newOrderDetail);
            }
        }

    }


    @Override
    public List<OrdersDTO> getAllOrders() {
        return mapping.convertToOrderDTOList(orderDAO.findAll());
    }

    @Override
    public OrderResponse getSelectedOrder(String orderId) {
        if(orderDAO.existsById(orderId)){
            return mapping.convertToOrderDTO(orderDAO.getReferenceById(orderId));
        }else {
            return new OrderErrorResponse(0, "Order Not Found");
        }
    }
}
