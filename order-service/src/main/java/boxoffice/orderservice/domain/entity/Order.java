package boxoffice.orderservice.domain.entity;

import boxoffice.orderservice.domain.enums.OrderStatus;
import boxoffice.orderservice.domain.vo.PriceVO;
import boxoffice.orderservice.exception.OrderDomainErrorCode;
import com.boxoffice.common.entity.AddressVO;
import com.boxoffice.common.entity.BaseEntity;
import com.boxoffice.common.exception.BaseException;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_order")
public class Order extends BaseEntity {

  @Column(name = "producer_company_id", nullable = false, columnDefinition = "VARCHAR(36)")
  private String producerCompanyId;

  @Column(name = "receiver_company_id", nullable = false, columnDefinition = "VARCHAR(36)")
  private String receiverCompanyId;

  @Embedded
  private PriceVO totalPrice;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, length = 30)
  private OrderStatus status;

  @Column(name = "request", length = 100)
  private String request;

  @Embedded
  private AddressVO addressVo;

  public static Order create(String producerOrderId,
      String receiverCompanyId,
      AddressVO addressVo,
      PriceVO totalPrice,
      String request) {
    validateCompanyId(producerOrderId);
    validateCompanyId(receiverCompanyId);

    return Order.builder()
        .producerOrderId(producerOrderId)
        .receiverCompanyId(receiverCompanyId)
        .addressVo(addressVo)
        .totalPrice(totalPrice)
        .request(request)
        .build();
  }

  @Builder(access = AccessLevel.PRIVATE)
  private Order(String producerOrderId,
      String receiverCompanyId,
      AddressVO addressVo,
      PriceVO totalPrice,
      String request) {
    this.producerCompanyId = producerOrderId;
    this.receiverCompanyId = receiverCompanyId;
    this.addressVo = addressVo;
    this.totalPrice = totalPrice;
    this.request = request;
  }

  private static void validateCompanyId(String companyId) {
    if (companyId == null || companyId.isBlank())
      throw new BaseException(OrderDomainErrorCode.INVALID_COMPANY_ID);
  }
}
