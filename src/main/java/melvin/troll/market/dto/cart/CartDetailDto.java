package melvin.troll.market.dto.cart;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailDto {
    private Integer cartId;
    private Integer productId;
    private Integer quantity;
    private String sellerId;
    private BigDecimal price;
}
