package boxoffice.orderservice.domain.vo;

import boxoffice.orderservice.exception.OrderErrorCode;
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
public class PriceVO {

  @Column(name = "price", nullable = false)
  private int value;

  public static PriceVO create(int value) {
    validate(value);
    PriceVO price = new PriceVO();
    price.value = value;
    return price;
  }

  public PriceVO add(PriceVO other) {
    return PriceVO.create(this.value + other.value);
  }

  private static void validate(int value) {
    if (value < 0)
      throw new BaseException(OrderErrorCode.INVALID_PRICE);
  }
}
