package lk.ijse.gdse68.clothingposspring.dao;

import lk.ijse.gdse68.clothingposspring.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDAO extends JpaRepository<Customer, String> {
}
