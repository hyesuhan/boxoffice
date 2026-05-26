package boxoffice.orderservice.infra.client;

import boxoffice.orderservice.infra.client.dto.DeliveryCreateRequestDto;
import boxoffice.orderservice.infra.client.dto.DeliveryCreateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "delivery-service")
public interface DeliveryFeignClient {

  @PostMapping("/api/internal/deliveries")
  DeliveryCreateResponse createDelivery(DeliveryCreateRequestDto request);
}
