package melvin.troll.market.dto.shipment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import melvin.troll.market.model.Shipment;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentHeaderDto {
    private Integer id;
    private String companyName;
    private String price;
    private String service;

    public static List<ShipmentHeaderDto> toList(List<Shipment> shipments) {
        List<ShipmentHeaderDto> result = new ArrayList<>();
        List<Shipment> tempContainer = new ArrayList<>();
        shipments.stream()
                .map(shipment -> {
                    return new ShipmentHeaderDto(
                            shipment.getId(),
                            shipment.getCompanyName(),
                            NumberFormat.getCurrencyInstance(new Locale(
                                    "id", "ID")).format(shipment.getPrice()),
                            shipment.getService() ? "Yes" : "No"
                    );
                }).forEach(result::add);
        return result;
    }
}
