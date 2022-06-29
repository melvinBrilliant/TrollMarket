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
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ShipID", nullable = false)
    private Integer id;

    @Column(name = "CompanyName", nullable = false, length = 100)
    private String companyName;

    @Column(name = "Price", nullable = false, precision = 19, scale = 4)
    private BigDecimal price;

    @Column(name = "Service")
    private Boolean service;

    @OneToMany(mappedBy = "shipID")
    private Set<CartDetail> cartDetails = new LinkedHashSet<>();

}