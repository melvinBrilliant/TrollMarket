package melvin.troll.market.dao;

import melvin.troll.market.model.CartDetail;
import melvin.troll.market.model.CartDetailId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartDetailRepository extends JpaRepository<CartDetail, CartDetailId> {
}