package melvin.troll.market.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import melvin.troll.market.model.Product;
import melvin.troll.market.validation.Compare;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductHeaderDto {
    private Integer id;
    private String productName;
    private String price; // Convert into IDR Currency format

    public static List<ProductHeaderDto> toList(List<Product> products) {
        List<ProductHeaderDto> result = new ArrayList<>();
        products.stream()
                .map((product) -> {
                    return new ProductHeaderDto(
                            product.getId(),
                            product.getProductName(),
                            NumberFormat.getCurrencyInstance(new Locale("id", "ID"))
                                    .format(product.getPrice())
                    );
                }).forEach(result::add);

        return result;
    }
}
