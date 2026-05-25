package boxoffice.orderservice.application.client.dto;

import java.util.UUID;

public record UserInfoResponseDto(
    UUID userId,
    String email,
    String name,
    String role,
    UUID hubId,
    String status,
    UUID companyId
) {
}
