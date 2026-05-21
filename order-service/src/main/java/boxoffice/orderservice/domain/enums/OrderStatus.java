package boxoffice.orderservice.domain.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OrderStatus {
  PENDING("주문 접수"),
  PREPARING("상품 준비 중"),
  DELIVERY_REQUESTED("배송 요청"),
  CANCELLED("주문 취소");

  private final String description;

}
