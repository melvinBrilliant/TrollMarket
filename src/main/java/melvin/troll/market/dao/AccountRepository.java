package melvin.troll.market.dao;

import melvin.troll.market.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {
    @Query(value = """
            SELECT COUNT(*)
            FROM Account AS acc
            WHERE acc.id = :username
            """)
    Long alreadyExistedAccounts(String username);
}