package melvin.troll.market.service.category;

import melvin.troll.market.dto.category.CategoryHeaderDto;
import melvin.troll.market.dto.category.UpsertCategoryDto;

import java.util.List;

public interface ICategoryService {
    UpsertCategoryDto upsertCategory(UpsertCategoryDto dto);
    List<CategoryHeaderDto> findAllCategories();
}
