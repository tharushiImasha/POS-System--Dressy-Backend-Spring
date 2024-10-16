package lk.ijse.gdse68.clothingposspring.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_details")

public class OrderDetails {
    @Id
    private String order_detail_id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonBackReference
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "costume_id", nullable = false)
    private Item item;

    private int quantity;
}
