package melvin.troll.market.model;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Account {
    @Id
    @Column(name = "Username", nullable = false, length = 50)
    private String id;

    @Column(name = "Password", nullable = false, length = 200)
    private String password;

    @Column(name = "Role", nullable = false, length = 6)
    private String role;

    @Column(name = "FullName", length = 100)
    private String fullName;

    @Column(name = "Address", length = 250)
    private String address;

    @Column(name = "Balance", precision = 19, scale = 4)
    private BigDecimal balance;

    @OneToMany(mappedBy = "sellerID")
    private Set<Product> products = new LinkedHashSet<>();

    @OneToMany(mappedBy = "customerID")
    private Set<Cart> carts = new LinkedHashSet<>();

    public void isAppropriateRole(String assignedRole, String message) {
        if (!role.equals(assignedRole)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    message
            );
        }
    }
}