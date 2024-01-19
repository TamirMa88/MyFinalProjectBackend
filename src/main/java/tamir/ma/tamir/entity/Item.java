package tamir.ma.tamir.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table (uniqueConstraints = {@UniqueConstraint(columnNames = "title")})
public class Item {
    @Id
    @GeneratedValue
    @NotNull
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @Length(max = 1024)
    private String img;

    private String barcode;

    @Min(value = 1, message = "price must be atleast 1")
    private double price;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
    @JsonIgnore
    private Set<CartItem> cartItems;

}
