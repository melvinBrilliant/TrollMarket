package melvin.troll.market.service.category;

import melvin.troll.market.dao.CategoryRepository;
import melvin.troll.market.dto.category.CategoryHeaderDto;
import melvin.troll.market.dto.category.UpsertCategoryDto;
import melvin.troll.market.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class CategoryService implements ICategoryService{
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public UpsertCategoryDto upsertCategory(UpsertCategoryDto dto) {
        Category category = Category.builder()
                .categoryName(dto.getCategoryName())
                .build();
        categoryRepository.save(category);
        return dto;
    }

    @Override
    public List<CategoryHeaderDto> findAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryHeaderDto> result = new ArrayList<>();
        categories.stream()
                .map(CategoryHeaderDto::set)
                .forEach(result::add);
        return result;
    }

}
