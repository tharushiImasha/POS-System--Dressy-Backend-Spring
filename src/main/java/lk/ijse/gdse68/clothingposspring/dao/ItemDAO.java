package lk.ijse.gdse68.clothingposspring.dao;

import lk.ijse.gdse68.clothingposspring.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemDAO extends JpaRepository<Item,String> {
}
