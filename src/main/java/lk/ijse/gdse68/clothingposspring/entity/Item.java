package lk.ijse.gdse68.clothingposspring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "item")

public class Item implements Serializable {
    @Id
    private String costume_id;
    private String type;
    private String color;
    private int amount;
    private String price;
    @Column(columnDefinition = "LONGTEXT")
    private String costume_picture;
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetails;
}
