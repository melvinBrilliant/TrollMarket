package melvin.troll.market.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class CartDetailId implements Serializable {
    private static final long serialVersionUID = -8265469458057339150L;
    @Column(name = "CartID", nullable = false)
    private Integer cartID;

    @Column(name = "ProductID", nullable = false)
    private Integer productID;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CartDetailId entity = (CartDetailId) o;
        return Objects.equals(this.productID, entity.productID) &&
                Objects.equals(this.cartID, entity.cartID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productID, cartID);
    }

}