package lk.ijse.gdse68.clothingposspring.util;

import lk.ijse.gdse68.clothingposspring.dao.CustomerDAO;
import lk.ijse.gdse68.clothingposspring.dto.CustomerDTO;
import lk.ijse.gdse68.clothingposspring.dto.ItemDTO;
import lk.ijse.gdse68.clothingposspring.dto.OrdersDTO;
import lk.ijse.gdse68.clothingposspring.entity.Customer;
import lk.ijse.gdse68.clothingposspring.entity.Item;
import lk.ijse.gdse68.clothingposspring.entity.Orders;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class Mapping {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CustomerDAO customerDAO;

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
    public OrdersDTO convertToOrderDTO(Orders orders) {
        return modelMapper.map(orders, OrdersDTO.class);
    }

    public Orders convertToOrderEntity(OrdersDTO ordersDTO) {
        String cusId = ordersDTO.getCus_id();
        Optional<Customer> customerOptional = customerDAO.findById(cusId);

        if (customerOptional.isPresent()) {
            Orders orders = modelMapper.map(ordersDTO, Orders.class);
            orders.setCustomer(customerOptional.get()); // Set the Customer object
            return orders;
        } else {
            // Handle the case where the customer is not found
            throw new IllegalArgumentException("Customer with ID " + cusId + " not found.");
        }
    }


    public List<OrdersDTO> convertToOrderDTOList(List<Orders> orders) {
        return modelMapper.map(orders, new TypeToken<List<OrdersDTO>>() {}.getType());
    }
}
