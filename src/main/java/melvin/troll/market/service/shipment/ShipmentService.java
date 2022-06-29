package melvin.troll.market.service.shipment;

import melvin.troll.market.dao.ShipmentRepository;
import melvin.troll.market.dto.shipment.ShipmentHeaderDto;
import melvin.troll.market.dto.shipment.UpsertShipmentDto;
import melvin.troll.market.model.Shipment;
import melvin.troll.market.validation.Compare;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.StreamHandler;
import java.util.stream.Stream;

@Service
public class ShipmentService implements IShipmentService{
    @Autowired
    private ShipmentRepository shipmentRepository;

    @Override
    public List<ShipmentHeaderDto> findAllShippers() {
        List<Shipment> shipments = shipmentRepository.findAll();
        return ShipmentHeaderDto.toList(shipments);
    }

    @Override
    public List<ShipmentHeaderDto> findAllActiveShippers() {
        List<ShipmentHeaderDto> activeShippers = findAllShippers();
        activeShippers.forEach(shipper -> {
            if (shipper.getService().equals("No")) {
                activeShippers.remove(shipper);
            }
        });
        return activeShippers;
    }

    @Override
    public ShipmentHeaderDto upsertShipment(UpsertShipmentDto dto) {
        Shipment shipment = Shipment.builder()
                .id(dto.getId())
                .companyName(dto.getCompanyName())
                .price(BigDecimal.valueOf(dto.getPrice()))
                .service(dto.getService() == null || dto.getService())
                .build();
        shipmentRepository.save(shipment);
        return new ShipmentHeaderDto(
                shipment.getId(),
                shipment.getCompanyName(),
                NumberFormat.getCurrencyInstance(new Locale("id", "ID")).format(shipment.getPrice()),
                shipment.getService() ? "Yes" : "No"
        );
    }
}
