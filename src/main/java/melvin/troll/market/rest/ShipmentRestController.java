package melvin.troll.market.rest;

import melvin.troll.market.dto.RestResponse;
import melvin.troll.market.dto.shipment.ShipmentHeaderDto;
import melvin.troll.market.dto.shipment.UpsertShipmentDto;
import melvin.troll.market.service.shipment.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/shipment")
public class ShipmentRestController {
    @Autowired
    private ShipmentService service;

    @GetMapping // boleh semua (admin, seller, buyer)
    public ResponseEntity<RestResponse<List<ShipmentHeaderDto>>> findAllActiveShippers() {
        List<ShipmentHeaderDto> allActiveShippers = service.findAllActiveShippers();
        String message = String.format("Showing all available %d shipment companies",
                allActiveShippers.size());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new RestResponse<>(
                        service.findAllActiveShippers(),
                        message,
                        "200"
                ));
    }

    @GetMapping("/all") // hanya admin
    public ResponseEntity<RestResponse<List<ShipmentHeaderDto>>> findAllShippers() {
        List<ShipmentHeaderDto> allShippers = service.findAllShippers();
        String message = String.format("Showing all %d shipment companies (both active and inactive).",
                allShippers.size());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new RestResponse<>(
                        service.findAllActiveShippers(),
                        message,
                        "200"
                ));
    }

    @PostMapping("/upsert")
    public ResponseEntity<RestResponse<ShipmentHeaderDto>> upsertShipment(
            @Valid @RequestBody UpsertShipmentDto dto
            ) {
        String message;
        if (dto.getId() == null) {
            message = String.format("New shipment company: %s has been created",
                    dto.getCompanyName());
        } else {
            message = String.format("Shipment company %s has been updated",
                    dto.getCompanyName());
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RestResponse<>(
                        service.upsertShipment(dto),
                        message,
                        "201"
                ));
    }
}
