package melvin.troll.market.service.shipment;

import melvin.troll.market.dto.shipment.ShipmentHeaderDto;
import melvin.troll.market.dto.shipment.UpsertShipmentDto;

import java.util.List;

public interface IShipmentService {
    List<ShipmentHeaderDto> findAllShippers();
    List<ShipmentHeaderDto> findAllActiveShippers();
    ShipmentHeaderDto upsertShipment(UpsertShipmentDto dto);
}
