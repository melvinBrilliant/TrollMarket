package melvin.troll.market.rest;

import melvin.troll.market.dao.ProductRepository;
import melvin.troll.market.dto.RestResponse;
import melvin.troll.market.dto.product.ProductHeaderDto;
import melvin.troll.market.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
