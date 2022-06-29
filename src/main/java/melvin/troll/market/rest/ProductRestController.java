package melvin.troll.market.rest;

import melvin.troll.market.dao.ProductRepository;
import melvin.troll.market.dto.RestResponse;
import melvin.troll.market.dto.product.ProductHeaderDto;
import melvin.troll.market.dto.product.UpsertProductDto;
import melvin.troll.market.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductRestController {
    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<RestResponse<List<ProductHeaderDto>>> findAllProducts() {
        List<ProductHeaderDto> products = service.findAllProducts();
        String message = String.format("Showing %d available products",
                products.size());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new RestResponse<>(
                        service.findAllProducts(),
                        message,
                        "200"
                ));
    }

    @PostMapping("/insert")
    public ResponseEntity<RestResponse<UpsertProductDto>> insertProduct(
            @Valid @RequestBody UpsertProductDto dto,
            Authentication authentication) {
        String message = String.format("New product: %s has been added successfully",
                dto.getProductName());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RestResponse<>(
                        service.insertProduct(dto, authentication),
                        message,
                        "201"
                ));
    }

    @PostMapping("/update")
    public ResponseEntity<RestResponse<UpsertProductDto>> updateProduct(
            @Valid @RequestBody UpsertProductDto dto,
            Authentication authentication
    ) {
        String message = String.format("Product %s has been updated",
                dto.getProductName());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RestResponse<>(
                        service.updateProduct(dto, authentication),
                        message,
                        "201"
                ));
    }
}
