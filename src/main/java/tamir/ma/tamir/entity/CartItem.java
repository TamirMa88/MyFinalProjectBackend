package tamir.ma.tamir.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "cart-items")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItem {
    @Id
    @GeneratedValue
    private Long id;
    private int quantity;

    @ManyToOne
    @JoinTable(name = "cart_items_item",
            joinColumns = {@JoinColumn(name = "cart_item_id")},
            inverseJoinColumns = {@JoinColumn(name = "item_id")}
    )
    private Item item;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Order order;

}
