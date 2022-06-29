package melvin.troll.market.rest;

import melvin.troll.market.dto.RestResponse;
import melvin.troll.market.dto.account.AccountBalanceDto;
import melvin.troll.market.dto.account.RegisterAdminDto;
import melvin.troll.market.dto.account.RegisterBuyerSellerDto;
import melvin.troll.market.dto.account.RegisterJwtDto;
import melvin.troll.market.service.MyUserDetailsService;
import melvin.troll.market.service.account.AccountService;
import melvin.troll.market.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AccountRestController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("auth")
    public Authentication index(Authentication authentication) {
        return authentication;
    }

    @PostMapping("/register/admin")
    public ResponseEntity<RestResponse<RegisterAdminDto>> adminRegistration(
            @Valid @RequestBody RegisterAdminDto registerAdminDto) {
        String username = registerAdminDto.getUsername();
        String message = String.format("New admin %s has been added successfully",
                registerAdminDto.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RestResponse<>(
                        accountService.registerAdminAccount(registerAdminDto),
                        message,
                        "201"
                ));
    }

    @PostMapping("/register/seller")
    public ResponseEntity<RestResponse<RegisterBuyerSellerDto>> sellerRegistration(
            @Valid @RequestBody RegisterBuyerSellerDto registerBuyerSellerDto
    ) {
        String username = registerBuyerSellerDto.getUsername();
        String message = String.format("New seller %s has been added successfully",
                registerBuyerSellerDto.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RestResponse<>(
                        accountService.registerSellerAccount(registerBuyerSellerDto),
                        message,
                        "201"
                ));
    }

    @PostMapping("/register/buyer")
    public ResponseEntity<RestResponse<RegisterBuyerSellerDto>> buyerRegistration(
            @Valid @RequestBody RegisterBuyerSellerDto registerBuyerSellerDto
    ) {
        String username = registerBuyerSellerDto.getUsername();
        String message = String.format("New buyer %s has been added successfully",
                registerBuyerSellerDto.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RestResponse<>(
                        accountService.registerBuyerAccount(registerBuyerSellerDto),
                        message,
                        "201"
                ));
    }

    @PostMapping("/jwt")
    public ResponseEntity<RestResponse<String>> createAuthenticationToken(@RequestBody RegisterJwtDto jwtDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                jwtDto.getUsername(), jwtDto.getPassword()
        ));

        UserDetails userDetails = myUserDetailsService.loadUserByUsername(jwtDto.getUsername());
        String jwt = jwtUtil.generateToken(userDetails);

        String message = String.format("JWT Token for account %s has been successfully generated",
                jwtDto.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RestResponse<>(
                        jwt,
                        message,
                        "201"
                ));
    }

    @PostMapping("add-balance")
    public ResponseEntity<RestResponse<AccountBalanceDto>> addAccountsBalance(
            @RequestParam Double additionalBalance,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RestResponse<>(
                        accountService.addBuyersBalance(authentication, additionalBalance),
                        "Your balance has beed added",
                        "201"
                ));
    }

}
