package lk.ijse.gdse68.clothingposspring.dao;

import lk.ijse.gdse68.clothingposspring.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDAO extends JpaRepository<Orders, String> {
}
