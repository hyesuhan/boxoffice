package boxoffice.orderservice.infra.context;

import boxoffice.orderservice.application.client.dto.UserInfoResponseDto;
import boxoffice.orderservice.infra.exception.OrderDomainErrorCode;
import com.boxoffice.common.exception.BaseException;
import java.util.UUID;

public record UserContext(
    UUID userId,
    String role,
    String status,
    UUID companyId,
    UUID hubId
) {
  public static UserContext of(UserInfoResponseDto userInfo) {
    return new UserContext(
        userInfo.userId(),
        userInfo.role(),
        userInfo.status(),
        userInfo.companyId(),
        userInfo.hubId()
    );
  }

  public boolean isCompanyManager() {
    return "COMPANY_MANAGER".equals(this.role);
  }

  public boolean isHubManager() {
    return "HUB_MANAGER".equals(this.role);
  }

  public boolean isMasterAdmin() {
    return "MASTER".equals(this.role);
  }

  public boolean isDeliveryManager() {
    return "DELIVERY_MANAGER".equals(this.role);
  }

  public UUID getCompanyIdOrThrow() {
    if (companyId == null) {
      throw new BaseException(OrderDomainErrorCode.MISSING_COMPANY_ID);
    }
    return companyId;
  }

  public UUID getHubIdOrThrow() {
    if (hubId == null) {
      throw new BaseException(OrderDomainErrorCode.MISSING_HUB_ID);
    }
    return hubId;
  }
}
