package lk.ijse.gdse68.clothingposspring.service;

import lk.ijse.gdse68.clothingposspring.customObj.ItemResponse;
import lk.ijse.gdse68.clothingposspring.dto.ItemDTO;

import java.util.List;

public interface ItemService {
    void saveItem(ItemDTO itemDTO) ;
    void updateItem(ItemDTO itemDTO);
    void deleteItem(String itemId) ;
    List<ItemDTO> getAllItem() ;
    ItemResponse getSelectedItem(String itemId) ;
}
