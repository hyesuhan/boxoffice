package boxoffice.orderservice.infra.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserFeignClient {
  @GetMapping("/api/v1/users/keycloak/{keycloak_sub}")
  void getUserDetails(@PathVariable String keycloak_sub);
}
