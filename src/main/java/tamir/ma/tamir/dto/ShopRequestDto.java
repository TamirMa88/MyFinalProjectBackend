package tamir.ma.tamir.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopRequestDto {
    @NotNull
    @Size(min = 2, max = 255)
    private String title;
    @NotNull
    @Size(min = 2, max = 1000)
    private String description;
    private String barcode;

    private String img;
    @Min(value = 1 ,message = "price must be at-least 1")
    private double price;
}