package boxoffice.orderservice.infra.event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderCreatedEvent(
    UUID orderId,
    UUID producerCompanyId,
    UUID receiverCompanyId,
    UUID originHubId,
    UUID destinationHubId,
    String request,
    List<OrderProductEventDto> products,
    LocalDateTime createdAt
) {
  public record OrderProductEventDto(
      UUID productId,
      String productName,
      Integer quantity
  ) {}
}
