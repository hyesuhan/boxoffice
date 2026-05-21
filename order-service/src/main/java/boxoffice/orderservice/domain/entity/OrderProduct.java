package boxoffice.orderservice.domain.entity;

import boxoffice.orderservice.domain.vo.ProductSnapShot;
import boxoffice.orderservice.exception.OrderDomainErrorCode;
import com.boxoffice.common.entity.BaseEntity;
import com.boxoffice.common.exception.BaseException;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_order_products")
public class OrderProduct extends BaseEntity {

  @Column(name = "order_id", nullable = false)
  private UUID orderId;

  @Column(name = "product_id", nullable = false, columnDefinition = "VARCHAR(36)")
  private String productId;

  @Embedded
  private ProductSnapShot snapshot;


  public static OrderProduct create(UUID orderId, String productId, ProductSnapShot snapShot) {
    return OrderProduct.builder()
        .orderId(orderId)
        .productId(productId)
        .snapShot(snapShot)
        .build();
  }

  @Builder(access = AccessLevel.PRIVATE)
  private OrderProduct(UUID orderId, String productId, ProductSnapShot snapShot) {
    this.orderId = orderId;
    this.productId = productId;
    this.snapshot = snapShot;
  }

  private static void validateProductId(String productId) {
    if (productId == null || productId.isBlank())
      throw new BaseException(OrderDomainErrorCode.INVALID_PRODUCT_ID);
  }

}
