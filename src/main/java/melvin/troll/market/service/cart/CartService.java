package melvin.troll.market.service.cart;

import melvin.troll.market.dao.*;
import melvin.troll.market.dto.cart.CartDetailDto;
import melvin.troll.market.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CartService implements ICartService{
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartDetailRepository cartDetailRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<CartDetailDto> viewCart(Authentication authentications) {
        String username = authentications.getName();
        Account account = accountRepository.findById(username).get();

        boolean isBuyer = account.getRole().equals("BUYER");
        if (!isBuyer) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Only buyers have cart"
            );
        }

        return cartDetailRepository.viewCart(username);
    }

    @Override
    public List<CartDetailDto> addProductToCart(Authentication authentication, Integer productId, Integer quantity) {
        String username = authentication.getName();
        Account account = accountRepository.findById(username).get();

        boolean isBuyer = account.getRole().equals("BUYER");
        if (!isBuyer) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Only buyers are allowed to add products to cart");
        }

        boolean cartIsAvailable = cartRepository.findCart(username).isPresent();
        if (!cartIsAvailable) {
            Cart cart = Cart.builder()
                    .customerID(account)
                    .build();
            cartRepository.save(cart);
        }

        Cart cart = cartRepository.findCart(username).get();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Product not found"
                ));
        CartDetail cartDetail = CartDetail.builder()
                .id(CartDetailId.builder()
                        .cartID(cart.getId())
                        .productID(productId)
                        .build())
                .cartID(cart)
                .productID(product)
                .quantity(quantity)
                .build();
        cartDetailRepository.save(cartDetail);

        return cartDetailRepository.viewCart(username);
    }

    @Override
    public List<CartDetailDto> removeFromCart(Authentication authentication, Integer productId) {
        String username = authentication.getName();
        Account account = accountRepository.findById(username).get();
        account.isAppropriateRole("BUYER",
                "Only buyers are allowed to refactor their carts");

        Cart cart = cartRepository.findCart(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "You do not have any products in your cart"
                ));

        CartDetailId productToRemoveFromCart = CartDetailId.builder()
                .productID(productId)
                .cartID(cart.getId())
                .build();

        boolean productExistsInCart = cartDetailRepository.existsById(productToRemoveFromCart);
        if (!productExistsInCart) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Product not found in cart"
            );
        }

        cartDetailRepository.deleteById(productToRemoveFromCart);
        return cartDetailRepository.viewCart(username);
    }
}
