package melvin.troll.market.rest;

import melvin.troll.market.dto.RestResponse;
import melvin.troll.market.dto.category.CategoryHeaderDto;
import melvin.troll.market.dto.category.UpsertCategoryDto;
import melvin.troll.market.model.Category;
import melvin.troll.market.service.category.CategoryService;
import melvin.troll.market.validation.Compare;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryRestController {
    @Autowired
    CategoryService service;

    @GetMapping("/index")
    public ResponseEntity<RestResponse<List<CategoryHeaderDto>>> index() {
        List<CategoryHeaderDto> allCategories = service.findAllCategories();
        String message = String.format("Showing all %d available categories",
                allCategories.size());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new RestResponse<>(
                        allCategories,
                        message,
                        "200"
                ));
    }

    @PostMapping("/upsert")
    public ResponseEntity<RestResponse<Object>> upsertCategory(
            @Valid @RequestBody UpsertCategoryDto dto) {
        String message;
        if (dto.getId() == null) {
            message = String.format("New %s category has been created",
                    dto.getCategoryName());
        } else {
            message = String.format("Category %s has been updated",
                    dto.getCategoryName());
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RestResponse<>(
                        service.upsertCategory(dto),
                        message,
                        "201"
                ));
    }
}
