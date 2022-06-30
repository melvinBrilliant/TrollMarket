package melvin.troll.market.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "CartDetails")
public class CartDetail {
    @EmbeddedId
    private CartDetailId id;

    @MapsId("cartID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CartID", nullable = false)
    private Cart cartID;

    @MapsId("productID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ProductID", nullable = false)
    private Product productID;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ShipID")
    private Shipment shipID;

    @Column(name = "PricePerUnit")
    private BigDecimal pricePerUnit;

}