package melvin.troll.market.service.product;

import melvin.troll.market.dao.ProductRepository;
import melvin.troll.market.dto.product.ProductHeaderDto;
import melvin.troll.market.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;

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
}
