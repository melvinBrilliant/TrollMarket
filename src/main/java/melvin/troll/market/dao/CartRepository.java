package melvin.troll.market.dao;

import melvin.troll.market.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    @Query(value = """
            SELECT *
            FROM Cart c
            WHERE c.PurchaseDate IS NULL
                AND c.CustomerID = :username
            """, nativeQuery = true)
    Optional<Cart> findCart(String username);
}