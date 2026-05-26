package boxoffice.orderservice.infra.dto;

import java.util.UUID;

public record UserInfo(
    UUID userId,
    String name,
    String role,
    UUID hubId,
    String status,
    UUID companyId
) {
  public boolean isMaster(){
    return "MASTER".equals(role);
  }

  public boolean isHubManager() {
    return "HUB_MANAGER".equals(role);
  }

  public boolean isDelivery() {
    return "DELIVERY".equals(role);
  }

  public boolean isCompany() {
    return "COMPANY".equals(role);
  }
}
