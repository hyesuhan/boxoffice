package boxoffice.orderservice.infra.client;

import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "company-product-service")
public interface CompanyProductFeignClient {

  @GetMapping("/internal/v1/companies/{companyId}/order-role")
  void validateCompanyId(@PathVariable UUID companyId);

  // 재고 차감 - 재고 부족 시 주문 실패
  @PostMapping("/internal/v1/products/{productId}/stock/deduct")
  void deductProducts(@PathVariable UUID productId);

  // 주문 취소 또는 보상 처리 시 재고 복원
  @PostMapping("/internal/v1/products/{productId}/stock/restore")
  void restoreProducts(@PathVariable UUID productId);

  // 주문 상품 검증 - 주문 생성 전 상품 존재 여부, 삭제 여부, 재고 검증 등
  @GetMapping("/internal/v1/products/{productId}/orderable")
  void validateProducts(@PathVariable UUID productId);
}