package melvin.troll.market.service.account;

import melvin.troll.market.config.RestSecurityConfig;
import melvin.troll.market.dao.AccountRepository;
import melvin.troll.market.dto.account.RegisterAdminDto;
import melvin.troll.market.dto.account.RegisterBuyerSellerDto;
import melvin.troll.market.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements IAccountService{
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public RegisterAdminDto registerAdminAccount(RegisterAdminDto dto) {
        PasswordEncoder passwordEncoder = RestSecurityConfig.passwordEncoder();
        String hashPassword = passwordEncoder.encode(dto.getPassword());
        Account account = Account.builder()
                .id(dto.getUsername())
                .password(hashPassword)
                .role("ADMIN")
                .build();
        accountRepository.save(account);
        return dto;
    }

    @Override
    public RegisterBuyerSellerDto registerSellerAccount(RegisterBuyerSellerDto dto) {
        PasswordEncoder passwordEncoder = RestSecurityConfig.passwordEncoder();
        String hashPassword = passwordEncoder.encode(dto.getPassword());
        Account account = Account.builder()
                .id(dto.getUsername())
                .password(hashPassword)
                .role("SELLER")
                .fullName(dto.getFullName())
                .address(dto.getAddress())
                .build();
        accountRepository.save(account);
        return dto;
    }

    @Override
    public RegisterBuyerSellerDto registerBuyerAccount(RegisterBuyerSellerDto dto) {
        PasswordEncoder passwordEncoder = RestSecurityConfig.passwordEncoder();
        String hashPassword = passwordEncoder.encode(dto.getPassword());
        Account account = Account.builder()
                .id(dto.getUsername())
                .password(hashPassword)
                .role("BUYER")
                .fullName(dto.getFullName())
                .address(dto.getAddress())
                .build();
        accountRepository.save(account);
        return dto;
    }

    @Override
    public String getAccountRole(String username) {
        Account account = accountRepository.findById(username).get();
        return account.getRole();
    }

    @Override
    public Boolean checkEsxistingAccount(String username) {
        long totalAccount = accountRepository.alreadyExistedAccounts(username);
        return totalAccount > 0; // if totalAccount = 0, return false
    }
}
