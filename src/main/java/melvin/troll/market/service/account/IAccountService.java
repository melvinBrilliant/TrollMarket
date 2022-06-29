package melvin.troll.market.service.account;

import melvin.troll.market.dto.account.AccountBalanceDto;
import melvin.troll.market.dto.account.RegisterAdminDto;
import melvin.troll.market.dto.account.RegisterBuyerSellerDto;
import org.springframework.security.core.Authentication;

public interface IAccountService {
    RegisterAdminDto registerAdminAccount(RegisterAdminDto dto);
    RegisterBuyerSellerDto registerSellerAccount(RegisterBuyerSellerDto dto);
    RegisterBuyerSellerDto registerBuyerAccount(RegisterBuyerSellerDto dto);
    String getAccountRole(String username);
    Boolean checkEsxistingAccount(String username);
    AccountBalanceDto addBuyersBalance(Authentication authentication, Double additionalBalance);
}
