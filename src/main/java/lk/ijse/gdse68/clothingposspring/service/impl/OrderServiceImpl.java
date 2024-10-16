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

        var order = mapping.convertToOrderEntity(ordersDTO);
        var savedOrder = orderDAO.save(order);

        if (savedOrder != null) {
            List<OrderDetailsDTO> orderDetails = ordersDTO.getOrder_details();
            for (OrderDetailsDTO orderDetail : orderDetails) {
                Item item = itemDAO.findById(orderDetail.getCostume_id())
                        .orElseThrow(() -> new DataPersistFailedException("Item not found"));
                OrderDetails newOrderDetail = new OrderDetails(
                        generateOrderDetailID(),
                        order,
                        item,
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
        if (orderDAO.existsById(orderId)) {
            return mapping.convertToOrderDTO(orderDAO.getReferenceById(orderId));
        } else {
            return new OrderErrorResponse(0, "Order Not Found");
        }
    }

    private String generateOrderDetailID() {
        if (orderDetailsDAO.count() == 0) {
            return "OD1";
        } else {
            String lastId = orderDetailsDAO.findAll().get(orderDetailsDAO.findAll().size() - 1).getOrder_detail_id();
            int newId = Integer.parseInt(lastId.substring(1)) + 1;
            if (newId < 10) {
                return "OD" + newId;
            } else if (newId < 100) {
                return "OD0" + newId;
            } else {
                return "OD" + newId;
            }

        }
    }
}
