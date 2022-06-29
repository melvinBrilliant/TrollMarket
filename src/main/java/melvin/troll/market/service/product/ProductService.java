package melvin.troll.market.service.product;

import melvin.troll.market.dao.AccountRepository;
import melvin.troll.market.dao.CategoryRepository;
import melvin.troll.market.dao.ProductRepository;
import melvin.troll.market.dto.product.ProductHeaderDto;
import melvin.troll.market.dto.product.UpsertProductDto;
import melvin.troll.market.model.Account;
import melvin.troll.market.model.Category;
import melvin.troll.market.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Product> fetchAllContinuedProducts() {
        List<Product> result = new ArrayList<>();
        List<Product> allProducts = productRepository.findAll();
        allProducts.forEach((product) -> {
            if (!product.getDiscontinued()) {
                result.add(product);
            }
        });
        return result;
    }

    @Override
    public List<ProductHeaderDto> findAllProducts() {
        List<Product> products = fetchAllContinuedProducts();
        return ProductHeaderDto.toList(products);
    }

    @Override
    public UpsertProductDto insertProduct(UpsertProductDto dto, Authentication authentication) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Category not found"
                ));

        Account seller = accountRepository.findById(authentication.getName()).get();

        Product product = Product.builder()
                .productName(dto.getProductName())
                .categoryID(category)
                .description(dto.getDescription())
                .price(BigDecimal.valueOf(dto.getPrice()))
                .discontinued(false)
                .sellerID(seller)
                .build();
        productRepository.save(product);
        return dto;
    }

    @Override
    public UpsertProductDto updateProduct(UpsertProductDto dto, Authentication authentication) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Category not found"
                ));

        if (dto.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Choose a product to be updated!");
        }

        Product product = productRepository.findById(dto.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Product not found"
                ));
        String loggedSeller = authentication.getName();
        String productOwner = product.getSellerID().getId();
        if (!loggedSeller.equals(productOwner)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You are not allowed to edit this product, because this product is not owned by you");
        }
        return dto;
    }
}
