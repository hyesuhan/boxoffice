package boxoffice.orderservice.infra.client.dto;

import java.util.UUID;

public record CompanyInfo(
    UUID companyId,
    UUID hubId,
    boolean isActive
) {
}
