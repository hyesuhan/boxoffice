package boxoffice.orderservice.infra.client.dto;

import java.util.UUID;

public record DeliveryCreateResponse(
    UUID deliveryId
) {
}
