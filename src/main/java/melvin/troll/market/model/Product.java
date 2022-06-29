package melvin.troll.market.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID", nullable = false)
    private Integer id;

    @Column(name = "ProductName", nullable = false, length = 100)
    private String productName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CategoryID", nullable = false)
    private Category categoryID;

    @Column(name = "Description", nullable = false, length = 200)
    private String description;

    @Column(name = "Price", nullable = false, precision = 19, scale = 4)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SellerID", nullable = false)
    private Account sellerID;

    @Column(name = "Discontinued")
    private Boolean discontinued;

    @OneToMany(mappedBy = "productID")
    private Set<CartDetail> cartDetails = new LinkedHashSet<>();

}