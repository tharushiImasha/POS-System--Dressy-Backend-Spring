package lk.ijse.gdse68.clothingposspring.util;

import lk.ijse.gdse68.clothingposspring.dao.CustomerDAO;
import lk.ijse.gdse68.clothingposspring.dao.ItemDAO;
import lk.ijse.gdse68.clothingposspring.dao.OrderDAO;
import lk.ijse.gdse68.clothingposspring.dto.CustomerDTO;
import lk.ijse.gdse68.clothingposspring.dto.ItemDTO;
import lk.ijse.gdse68.clothingposspring.dto.OrderDetailsDTO;
import lk.ijse.gdse68.clothingposspring.dto.OrdersDTO;
import lk.ijse.gdse68.clothingposspring.entity.Customer;
import lk.ijse.gdse68.clothingposspring.entity.Item;
import lk.ijse.gdse68.clothingposspring.entity.OrderDetails;
import lk.ijse.gdse68.clothingposspring.entity.Orders;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class Mapping {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CustomerDAO customerDAO;
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private ItemDAO itemDAO;

    public CustomerDTO convertToDTO(Customer customer) {
        return modelMapper.map(customer, CustomerDTO.class);
    }
    public Customer convertToEntity(CustomerDTO customerDTO) {
        return modelMapper.map(customerDTO, Customer.class);
    }

    public List<CustomerDTO> convertToDTO(List<Customer> customers) {
        return modelMapper.map(customers, new TypeToken<List<CustomerDTO>>() {}.getType());
    }

    //Item Matters mapping
    public ItemDTO convertToItemDTO(Item item) {
        return modelMapper.map(item, ItemDTO.class);
    }

    public Item convertToItemEntity(ItemDTO itemDTO) {
        return modelMapper.map(itemDTO, Item.class);
    }

    public List<ItemDTO> convertToItemDTOList(List<Item> items) {
        return modelMapper.map(items, new TypeToken<List<ItemDTO>>() {}.getType());
    }

    //Order Matters mapping
    public Orders convertToOrderEntity(OrdersDTO ordersDTO) {
        String cusId = ordersDTO.getCus_id();
        Optional<Customer> customerOptional = customerDAO.findById(cusId);

        if (customerOptional.isPresent()) {
            Orders orders = modelMapper.map(ordersDTO, Orders.class);
            orders.setCustomer(customerOptional.get());
            return orders;
        } else {
            throw new IllegalArgumentException("Customer with ID " + cusId + " not found.");
        }
    }

    public List<OrdersDTO> convertToOrderDTOList(List<Orders> ordersList) {
        return ordersList.stream()
                .map(this::convertToOrderDTO)
                .collect(Collectors.toList());
    }

    public OrdersDTO convertToOrderDTO(Orders orders) {
        OrdersDTO ordersDTO = new OrdersDTO();

        ordersDTO.setOrder_id(orders.getOrder_id());
        ordersDTO.setTotal(orders.getTotal());
        ordersDTO.setDate(orders.getDate());

        if (orders.getCustomer() != null) {
            ordersDTO.setCus_id(orders.getCustomer().getCus_id());
        }

        List<OrderDetailsDTO> orderDetailsDTOList = orders.getOrderDetails()
                .stream()
                .map(this::convertToOrderDetailsDTO)
                .collect(Collectors.toList());
        ordersDTO.setOrder_details(orderDetailsDTOList);

        return ordersDTO;
    }

    private OrderDetailsDTO convertToOrderDetailsDTO(OrderDetails orderDetails) {
        // Assuming you're using a similar method to convert OrderDetails entity to DTO
        return modelMapper.map(orderDetails, OrderDetailsDTO.class);
    }
}
