package boxoffice.orderservice.domain.vo;

import boxoffice.orderservice.exception.OrderDomainErrorCode;
import com.boxoffice.common.exception.BaseException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductSnapShot {

  @Column(name = "product_name", nullable = false)
  private String productName;

  @Column(name = "unit_price", nullable = false)
  private Integer unitPrice;

  @Column(name = "quantity", nullable = false)
  private Integer quantity;

  public static ProductSnapShot create(String name, Integer price, Integer quantity) {
    validate(name, price, quantity);
    ProductSnapShot snapShot = new ProductSnapShot();
    snapShot.productName = name;
    snapShot.unitPrice = price;
    snapShot.quantity = quantity;
    return snapShot;
  }

  private static void validate(String name, Integer price, Integer quantity) {
    if (name.isEmpty())
      throw new BaseException(OrderDomainErrorCode.INVALID_ORDER_PRODUCT);

    if (price == null || price < 0)
      throw new BaseException(OrderDomainErrorCode.INVALID_ORDER_PRODUCT);

    if (quantity == null || quantity < 0)
      throw new BaseException(OrderDomainErrorCode.INVALID_ORDER_PRODUCT);
  }
}
