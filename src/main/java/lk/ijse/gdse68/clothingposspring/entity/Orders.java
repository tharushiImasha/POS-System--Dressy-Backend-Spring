package lk.ijse.gdse68.clothingposspring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")

public class Orders {
    @Id
    private String order_id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cus_id")
    private Customer customer;
    private double total;
    private LocalDate date;
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetails> orderDetails;
}
