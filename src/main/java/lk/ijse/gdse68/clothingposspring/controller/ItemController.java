package lk.ijse.gdse68.clothingposspring.controller;

import lk.ijse.gdse68.clothingposspring.customObj.ItemResponse;
import lk.ijse.gdse68.clothingposspring.dto.ItemDTO;
import lk.ijse.gdse68.clothingposspring.exception.DataPersistFailedException;
import lk.ijse.gdse68.clothingposspring.exception.ItemNotFound;
import lk.ijse.gdse68.clothingposspring.service.ItemService;
import lk.ijse.gdse68.clothingposspring.util.AppUtil;
import lombok.RequiredArgsConstructor;
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

    @Autowired
    private final ItemService itemService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveItem(
            @RequestPart("type") String type,
            @RequestPart("color") String color,
            @RequestPart("amount") String amount,
            @RequestPart("price") String price,
            @RequestPart("costume_picture") MultipartFile costume_picture
    ){
        try {
            byte[] imageBytes = costume_picture.getBytes();
            String base64ProfilePic = AppUtil.toBase64Pic(imageBytes);

            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setType(type);
            itemDTO.setColor(color);
            itemDTO.setAmount(Integer.parseInt(amount));
            itemDTO.setPrice(price);
            itemDTO.setCostume_picture(base64ProfilePic);

            itemService.saveItem(itemDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);

        } catch (DataPersistFailedException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ItemDTO> getAllItems(){
        return itemService.getAllItem();
    }

    @GetMapping(value = "/{costume_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ItemResponse getSelectedItem(@PathVariable("costume_id") String id){
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
            return new ResponseEntity<>(HttpStatus.CREATED);

        } catch (DataPersistFailedException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping(value = "/{costume_id}")
    public ResponseEntity<Void> deleteItem(@PathVariable("costume_id") String id){
        try {
            itemService.deleteItem(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (ItemNotFound e ){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
