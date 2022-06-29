package melvin.troll.market.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterBuyerSellerDto {
    @NotBlank(message = "Username is required")
    @Size(max = 20, message = "Username can not be more than 20 characters")
    private String username;
    @NotBlank(message = "Password is required")
    @Size(max = 20, message = "Password can not be more than 20 characters")
    private String password;
    @NotBlank(message = "Please confirm your password")
    private String confirmPassword;
    @NotBlank(message = "Name is required")
    private String fullName;
    @NotBlank(message = "Address is required")
    private String address;
}
