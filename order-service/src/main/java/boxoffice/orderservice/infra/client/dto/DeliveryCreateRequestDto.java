package boxoffice.orderservice.infra.client.dto;

import java.util.UUID;

public record DeliveryCreateRequestDto(
    UUID orderId,
    UUID originHubId,
    UUID destinationHubId,
    String deliveryAddress,
    String deliveryDetailAddress,
    String requestSlackId,
    String request
) {
}
