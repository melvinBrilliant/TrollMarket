package melvin.troll.market.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CartID", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CustomerID", nullable = false)
    private Account customerID;

    @Column(name = "PurchaseDate")
    private LocalDate purchaseDate;

    @OneToMany(mappedBy = "cartID")
    private Set<CartDetail> cartDetails = new LinkedHashSet<>();

}