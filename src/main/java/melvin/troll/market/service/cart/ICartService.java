package melvin.troll.market.service.cart;

import melvin.troll.market.dto.cart.CartDetailDto;
import melvin.troll.market.dto.product.AddProductToCartDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ICartService {
    List<CartDetailDto> viewCart(Authentication authentications);
    String totalPriceInCart(List<CartDetailDto> cart);
    List<CartDetailDto> addProductToCart(Authentication authentication, AddProductToCartDto dto);
    List<CartDetailDto> removeFromCart(Authentication authentication, Integer productId);
    List<CartDetailDto> purchaseFromCart(Authentication authentication);
}
