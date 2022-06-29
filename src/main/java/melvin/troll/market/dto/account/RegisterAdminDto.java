package melvin.troll.market.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import melvin.troll.market.validation.Compare;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Compare(message = "Password does not match.", firstField = "password", secondField = "confirmPassword")
public class RegisterAdminDto {
    @NotBlank(message = "Username is required")
    @Size(max = 20, message = "Username can not be more than 20 characters")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "Please confirm your password")
    private String confirmPassword;
}
