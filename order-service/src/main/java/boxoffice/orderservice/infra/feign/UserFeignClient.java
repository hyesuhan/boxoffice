package boxoffice.orderservice.infra.feign;

import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserFeignClient {
  @GetMapping("/api/v1/users/keycloak/{keyclak_sub}")
  UserDetailResponseDto getUserDetails(
      @PathVariable("keycloak_sub") String keycloackSub
  );

  record UserDetailResponseDto(
      UUID id,
      String email,
      String name,
      String role,
      UUID hubId,
      UUID companyId,
      String status
  ) {}
}
