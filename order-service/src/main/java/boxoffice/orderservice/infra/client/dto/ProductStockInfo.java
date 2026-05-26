package boxoffice.orderservice.infra.client.dto;

import java.util.UUID;

public record ProductStockInfo(
    UUID productId,
    String productName,
    Integer unitPrice,
    boolean hasEnoughStock
) {
}
