package melvin.troll.market.service.cart;

import melvin.troll.market.dto.cart.CartDetailDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ICartService {
    List<CartDetailDto> viewCart(Authentication authentications);
    List<CartDetailDto> addProductToCart(Authentication authentication, Integer productId, Integer quantity);
    List<CartDetailDto> removeFromCart(Authentication authentication, Integer productId);
}
