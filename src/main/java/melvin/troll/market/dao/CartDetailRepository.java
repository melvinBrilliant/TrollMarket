package melvin.troll.market.dao;

import melvin.troll.market.dto.cart.CartDetailDto;
import melvin.troll.market.model.Cart;
import melvin.troll.market.model.CartDetail;
import melvin.troll.market.model.CartDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CartDetailRepository extends JpaRepository<CartDetail, CartDetailId> {
    @Query(value = """
            SELECT new melvin.troll.market.dto.cart.CartDetailDto (
                c.id,
            	p.id,
            	cd.quantity,
            	sell.id,
            	s.id,
            	(p.price * cd.quantity) + s.price
            )
            FROM CartDetail cd
            INNER JOIN cd.cartID AS c
            INNER JOIN cd.productID AS p
            INNER JOIN c.customerID AS buy
            INNER JOIN p.sellerID AS sell
            INNER JOIN cd.shipID AS s
            WHERE (buy.id = :username) AND (c.purchaseDate IS NULL)
            """)
    List<CartDetailDto> viewCart(String username);

    @Query(value = """
            SELECT *
            FROM CartDetails
            WHERE CartID = :cartId
            """, nativeQuery = true)
    List<CartDetail> findCartDetailByCartId(Integer cartId);
}