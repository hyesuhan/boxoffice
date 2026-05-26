package boxoffice.orderservice.infra.feign;

import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "company-product-service")
public interface CompanyProductFeignClient {

  /**
   * 업체 존재 + 활성 여부 확인.
   * 응답의 hubId로 출발/도착 허브를 결정함.
   * 주소는 배송 서비스 책임으로 이관 — 응답에서 제거.
   */
  @GetMapping("/internal/v1/companies/{companyId}/order-role")
  CompanyOrderRoleResponseDto validateCompanyId(@PathVariable UUID companyId);

  /**
   * 상품 주문 가능 여부 사전 검증.
   * 재고 차감 없음 — 존재 여부, 삭제 여부만 확인.
   */
  @GetMapping("/internal/v1/products/{productId}/orderable")
  ProductOrderableResponseDto validateProducts(@PathVariable UUID productId);

  /**
   * 재고 차감.
   * company-product-service 측에서 검증 + 차감을 하나의 트랜잭션으로 처리.
   * 재고 부족 시 409 Conflict 반환.
   */
  @PostMapping("/internal/v1/products/{productId}/stock/deduct")
  void deductProducts(
      @PathVariable UUID productId,
      @RequestBody StockDeductRequestDto request
  );

  /**
   * 재고 복원.
   * 보상 트랜잭션 시 호출.
   */
  @PostMapping("/internal/v1/products/{productId}/stock/restore")
  void restoreProducts(
      @PathVariable UUID productId,
      @RequestBody StockRestoreRequestDto request
  );

  record CompanyOrderRoleResponseDto(
      UUID companyId,
      UUID hubId,
      boolean isActive
  ) {}

  record ProductOrderableResponseDto(
      UUID productId,
      String productName,
      Integer unitPrice,
      boolean orderable
  ) {}

  record StockDeductRequestDto(int quantity) {}
  record StockRestoreRequestDto(int quantity) {}

}
