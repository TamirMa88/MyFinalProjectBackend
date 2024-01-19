package tamir.ma.tamir.dto;


import lombok.*;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShopRequestCartDto extends ShopRequestDto {
    private int quantity;
    private long itemId;
}