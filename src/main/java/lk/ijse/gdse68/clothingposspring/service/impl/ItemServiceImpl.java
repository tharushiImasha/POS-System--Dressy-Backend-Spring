package lk.ijse.gdse68.clothingposspring.service.impl;

import lk.ijse.gdse68.clothingposspring.customObj.ItemErrorResponse;
import lk.ijse.gdse68.clothingposspring.customObj.ItemResponse;
import lk.ijse.gdse68.clothingposspring.dao.ItemDAO;
import lk.ijse.gdse68.clothingposspring.dto.ItemDTO;
import lk.ijse.gdse68.clothingposspring.entity.Item;
import lk.ijse.gdse68.clothingposspring.exception.CustomerNotFound;
import lk.ijse.gdse68.clothingposspring.exception.DataPersistFailedException;
import lk.ijse.gdse68.clothingposspring.service.ItemService;
import lk.ijse.gdse68.clothingposspring.util.AppUtil;
import lk.ijse.gdse68.clothingposspring.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDAO itemDAO;
    @Autowired
    private Mapping mapping;

    @Override
    public void saveItem(ItemDTO itemDTO) {
//        itemDTO.setCostume_id(AppUtil.createItemId());
        var item = mapping.convertToItemEntity(itemDTO);
        var savedItem = itemDAO.save(item);

        if(savedItem == null){
            throw new DataPersistFailedException("Item not saved");
        }
    }

    @Override
    public void updateItem(ItemDTO itemDTO) {
        Optional<Item> tempItemEntity = itemDAO.findById(itemDTO.getCostume_id());
        if (!tempItemEntity.isPresent()) {
            throw new CustomerNotFound(itemDTO.getCostume_id());
        }
        else {
            tempItemEntity.get().setType(itemDTO.getType());
            tempItemEntity.get().setPrice(itemDTO.getPrice());
            tempItemEntity.get().setColor(itemDTO.getColor());
            tempItemEntity.get().setAmount(itemDTO.getAmount());
            tempItemEntity.get().setCostume_picture(itemDTO.getCostume_picture());
        }
    }

    @Override
    public void deleteItem(String itemId) {
        Optional<Item> tempItemEntity = itemDAO.findById(itemId);
        if (!tempItemEntity.isPresent()) {
            throw new CustomerNotFound("Item not found");
        }else {
            itemDAO.deleteById(itemId);
        }
    }

    @Override
    public List<ItemDTO> getAllItem() {
        return mapping.convertToItemDTOList(itemDAO.findAll());
    }

    @Override
    public ItemResponse getSelectedItem(String itemId) {
        if(itemDAO.existsById(itemId)){
            return mapping.convertToItemDTO(itemDAO.getReferenceById(itemId));
        }else {
            return new ItemErrorResponse(0, "Item Not Found");
        }
    }
}
