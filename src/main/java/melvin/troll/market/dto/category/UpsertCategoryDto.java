package melvin.troll.market.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertCategoryDto {
    private Integer id;
    @NotBlank(message = "Category name can not be empty")
    private String categoryName;
}
