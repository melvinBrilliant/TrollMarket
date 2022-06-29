package melvin.troll.market.dto.shipment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertShipmentDto {
    private Integer id;
    @NotBlank(message = "Shipment company name can not be empty!")
    private String companyName;
    @NotNull(message = "Shipment price can not be empty!")
    private Double price;
    private Boolean service;
}
