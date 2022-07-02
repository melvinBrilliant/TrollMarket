package melvin.troll.market.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProductToCartDto {
    private Integer productId;
    @NotNull(message = "Please choose 1 shipper company")
    private Integer shipId;
    @NotNull(message = "Please choose the quantity of the product")
    private Integer quantity;
}
