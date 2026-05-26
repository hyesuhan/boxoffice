package boxoffice.orderservice.infra.dto;

import java.util.UUID;

public record CompanyInfo(
    UUID companyId,
    UUID hubId,
    boolean isActive
) {
}
