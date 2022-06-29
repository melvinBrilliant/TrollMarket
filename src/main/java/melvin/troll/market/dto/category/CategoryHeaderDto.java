package melvin.troll.market.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import melvin.troll.market.model.Category;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryHeaderDto {
    private Integer categoryId;
    private String categoryName;

    public static CategoryHeaderDto set(Category category) {
        return new CategoryHeaderDto(
                category.getId(),
                category.getCategoryName()
        );
    }
}
