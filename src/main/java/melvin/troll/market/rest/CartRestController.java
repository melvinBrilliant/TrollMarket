package melvin.troll.market.rest;

import melvin.troll.market.dto.RestResponse;
import melvin.troll.market.dto.cart.CartDetailDto;
import melvin.troll.market.dto.product.AddProductToCartDto;
import melvin.troll.market.model.CartDetailId;
import melvin.troll.market.service.cart.CartService;
import melvin.troll.market.validation.Compare;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartRestController {
    @Autowired
    private CartService service;

    @GetMapping
    public ResponseEntity<RestResponse<List<CartDetailDto>>> viewMyCart(Authentication authentication) {
        List<CartDetailDto> productsInCart = service.viewCart(authentication);
        String totalPrice = service.totalPriceInCart(productsInCart);
        String message = String.format("""
                        Showing %s products in your cart, total price to pay: %s""",
                productsInCart.size(), totalPrice);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new RestResponse<>(
                        service.viewCart(authentication),
                        message,
                        "200"
                ));
    }

    @PostMapping("/add")
    public ResponseEntity<RestResponse<List<CartDetailDto>>> addProductToCart(
            Authentication authentication,
            @Valid @RequestBody AddProductToCartDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RestResponse<>(
                        service.addProductToCart(authentication, dto),
                        "Product has been added to your cart",
                        "201"
                ));
    }

    @PostMapping("/remove")
    public ResponseEntity<RestResponse<List<CartDetailDto>>> removeProductFromCart(
            Authentication authentication,
            @RequestParam Integer productId
    ) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new RestResponse<>(
                        service.removeFromCart(authentication, productId),
                        "Product successfully removed from cart",
                        "202"
                ));
    }

    @PostMapping("/purchase")
    public ResponseEntity<RestResponse<List<CartDetailDto>>> purchase(
            Authentication authentication
    ) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(new RestResponse<>(
                        service.purchaseFromCart(authentication),
                        "Successfully made a purchase",
                        "201"
                ));
    }
}

