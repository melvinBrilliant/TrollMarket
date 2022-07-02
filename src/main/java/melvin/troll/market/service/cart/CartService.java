package melvin.troll.market.service.cart;

import melvin.troll.market.dao.*;
import melvin.troll.market.dto.cart.CartDetailDto;
import melvin.troll.market.dto.product.AddProductToCartDto;
import melvin.troll.market.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class CartService implements ICartService{
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartDetailRepository cartDetailRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ShipmentRepository shipmentRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<CartDetailDto> viewCart(Authentication authentications) {
        String username = authentications.getName();
        Account account = accountRepository.findById(username).get();

        return cartDetailRepository.viewCart(username);
    }

    @Override
    public String totalPriceInCart(List<CartDetailDto> cart) {
        List<BigDecimal> pricesInCart = new ArrayList<>();
        cart.forEach((cartContent) -> {
            pricesInCart.add(cartContent.getTotalPrice());
        });
        var totalPrice = pricesInCart.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return NumberFormat.getCurrencyInstance(
                new Locale("id", "ID")).format(totalPrice);
    }

    @Override
    public List<CartDetailDto> addProductToCart(
            Authentication authentication,
            AddProductToCartDto dto) {
        String username = authentication.getName();
        Account account = accountRepository.findById(username).get();

        boolean cartIsAvailable = cartRepository.findCart(username).isPresent();
        if (!cartIsAvailable) {
            Cart cart = Cart.builder()
                    .customerID(account)
                    .build();
            cartRepository.save(cart);
        }

        Cart cart = cartRepository.findCart(username).get();
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Product not found"
                ));

        Shipment shipper = shipmentRepository.findById(dto.getShipId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Shipper not found"
                ));

        CartDetail cartDetail = CartDetail.builder()
                .id(CartDetailId.builder()
                        .cartID(cart.getId())
                        .productID(dto.getProductId())
                        .build())
                .cartID(cart)
                .productID(product)
                .quantity(dto.getQuantity())
                .shipID(shipper)
                .build();
        cartDetailRepository.save(cartDetail);

        return cartDetailRepository.viewCart(username);
    }

    @Override
    public List<CartDetailDto> removeFromCart(Authentication authentication, Integer productId) {
        String username = authentication.getName();
        Account account = accountRepository.findById(username).get();

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

    @Override
    public List<CartDetailDto> purchaseFromCart(Authentication authentication) {
        String username = authentication.getName();
        Account account = accountRepository.findById(username).get();

        List<BigDecimal> pricesInCart = new ArrayList<>();
        List<CartDetailDto> cartView = viewCart(authentication);
        if (cartView.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No products in cart");
        }
        cartView.forEach((cartContent) -> {
            pricesInCart.add(cartContent.getTotalPrice());
        });
        var totalPrice = pricesInCart.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        var accountBalance = account.getBalance();
        int checkFunds = accountBalance.compareTo(totalPrice);
        if (checkFunds < 0) {
            throw new ResponseStatusException(
                    HttpStatus.PAYMENT_REQUIRED,
                    "Your Balance is not enough, please refill your balance."
            );
        }

        account.setBalance(accountBalance.subtract(totalPrice));
        BigDecimal balance = account.getBalance();
        accountRepository.save(account);

        Cart cart = cartRepository.findCart(username).get();
        List<CartDetail> purchaseHistory = cartDetailRepository.findCartDetailByCartId(cart.getId());
        cart.setPurchaseDate(LocalDate.now());

        purchaseHistory.forEach((detail) -> {
            BigDecimal pricePerUnit = detail.getProductID().getPrice();
            detail.setPricePerUnit(pricePerUnit);
            cartDetailRepository.save(detail);
        });

        cartRepository.save(cart);

        return cartView;
    }
}
