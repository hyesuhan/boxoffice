package boxoffice.orderservice.domain.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OrderStatus {
  PENDING("주문 접수"),
  DELIVERY_REQUESTED("배송 요청"),
  DELIVERING("배송중"),
  DELIVERED("배송 완료"),
  CANCELLED("주문 취소");

  private final String description;

  public boolean canTransitionTo(OrderStatus next) {
    return switch (this) {
      case PENDING -> next == DELIVERY_REQUESTED || next == CANCELLED;
      case DELIVERY_REQUESTED -> next == DELIVERING,
      case DELIVERING -> next == DELIVERED;
      case DELIVERED, CANCELLED -> false;
    };
  }
}
