package lk.ijse.gdse68.clothingposspring.controller;

import lk.ijse.gdse68.clothingposspring.customObj.ItemResponse;
import lk.ijse.gdse68.clothingposspring.dto.ItemDTO;
import lk.ijse.gdse68.clothingposspring.exception.DataPersistFailedException;
import lk.ijse.gdse68.clothingposspring.exception.ItemNotFound;
import lk.ijse.gdse68.clothingposspring.service.ItemService;
import lk.ijse.gdse68.clothingposspring.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/item")
@RequiredArgsConstructor
public class ItemController {

    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private final ItemService itemService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveItem(
            @RequestPart("costume_id") String costumeId,
            @RequestPart("type") String type,
            @RequestPart("color") String color,
            @RequestPart("amount") String amount,
            @RequestPart("price") String price,
            @RequestPart("costume_picture") MultipartFile costume_picture
    ){
        logger.info("Received request to save item with ID: {}", costumeId);
        try {
            byte[] imageBytes = costume_picture.getBytes();
            String base64ProfilePic = AppUtil.toBase64Pic(imageBytes);

            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setCostume_id(costumeId);
            itemDTO.setType(type);
            itemDTO.setColor(color);
            itemDTO.setAmount(Integer.parseInt(amount));
            itemDTO.setPrice(price);
            itemDTO.setCostume_picture(base64ProfilePic);

            itemService.saveItem(itemDTO);
            logger.info("Item saved successfully: {}", itemDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);

        } catch (DataPersistFailedException e) {
            logger.error("Data persist failed for item with ID: {}", costumeId, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while saving item with ID: {}", costumeId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ItemDTO> getAllItems(){
        logger.info("Received request to retrieve all items");
        return itemService.getAllItem();
    }

    @GetMapping(value = "/{costume_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ItemResponse getSelectedItem(@PathVariable("costume_id") String id){
        logger.info("Received request to retrieve item with ID: {}", id);
        return itemService.getSelectedItem(id);
    }

    @PatchMapping(value = "/{costume_id}", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateItem(@PathVariable("costume_id") String id,
            @RequestPart("type") String type,
            @RequestPart("color") String color,
            @RequestPart("amount") String amount,
            @RequestPart("price") String price,
            @RequestPart("costume_picture") MultipartFile costume_picture
    ){
        logger.info("Received request to update item with ID: {}", id);
        try {
            byte[] imageBytes = costume_picture.getBytes();
            String base64ProfilePic = AppUtil.toBase64Pic(imageBytes);

            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setCostume_id(id);
            itemDTO.setType(type);
            itemDTO.setColor(color);
            itemDTO.setAmount(Integer.parseInt(amount));
            itemDTO.setPrice(price);
            itemDTO.setCostume_picture(base64ProfilePic);

            itemService.updateItem(itemDTO);
            logger.info("Item updated successfully: {}", itemDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);

        } catch (DataPersistFailedException e) {
            logger.error("Data persist failed for item with ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while updating item with ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping(value = "/{costume_id}")
    public ResponseEntity<Void> deleteItem(@PathVariable("costume_id") String id){
        logger.info("Received request to delete item with ID: {}", id);
        try {
            itemService.deleteItem(id);
            logger.info("Item deleted successfully: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (ItemNotFound e ){
            logger.error("Item with ID: {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            logger.error("Unexpected error occurred while deleting item with ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
