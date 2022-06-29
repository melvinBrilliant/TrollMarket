package melvin.troll.market.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertProductDto {
    private Integer id;
    @NotBlank(message = "Product name can not be empty")
    private String productName;
    @NotNull(message = "Category can not be empty")
    private Integer categoryId;
    private String description;
    @NotNull(message = "Price can not be empty")
    private Double price;
    private Boolean discontinue;
}
