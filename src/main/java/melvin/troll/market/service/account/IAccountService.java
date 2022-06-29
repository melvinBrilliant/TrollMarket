package melvin.troll.market.service.account;

import melvin.troll.market.dto.account.RegisterAdminDto;
import melvin.troll.market.dto.account.RegisterBuyerSellerDto;

public interface IAccountService {
    RegisterAdminDto registerAdminAccount(RegisterAdminDto dto);
    RegisterBuyerSellerDto registerSellerAccount(RegisterBuyerSellerDto dto);
    RegisterBuyerSellerDto registerBuyerAccount(RegisterBuyerSellerDto dto);
    String getAccountRole(String username);
    Boolean checkEsxistingAccount(String username);
}
