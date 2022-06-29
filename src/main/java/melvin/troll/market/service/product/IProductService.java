package melvin.troll.market.service.product;

import melvin.troll.market.dto.product.ProductHeaderDto;
import melvin.troll.market.dto.product.UpsertProductDto;
import melvin.troll.market.model.Product;
import melvin.troll.market.validation.Compare;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IProductService {
    List<Product> fetchAllContinuedProducts();
    List<ProductHeaderDto> findAllProducts();
    UpsertProductDto insertProduct(UpsertProductDto dto, Authentication authentication);
    UpsertProductDto updateProduct(UpsertProductDto dto, Authentication authentication);
}
