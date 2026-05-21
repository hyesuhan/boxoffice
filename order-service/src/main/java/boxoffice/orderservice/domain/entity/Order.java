package boxoffice.orderservice.domain.entity;

import boxoffice.orderservice.domain.enums.OrderStatus;
import boxoffice.orderservice.domain.vo.TotalPrice;
import boxoffice.orderservice.exception.OrderDomainErrorCode;
import com.boxoffice.common.entity.AddressVO;
import com.boxoffice.common.entity.BaseEntity;
import com.boxoffice.common.exception.BaseException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_orders")
public class Order extends BaseEntity {

  @Column(name = "producer_company_id", nullable = false)
  private UUID producerCompanyId;

  @Column(name = "receiver_company_id", nullable = false)
  private UUID receiverCompanyId;

  @Column(name = "delivery_id")
  private UUID deliveryId;

  @Embedded
  private TotalPrice totalPrice;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, length = 30)
  private OrderStatus status;

  @Column(name = "request", length = 100)
  private String request;

  @Embedded
  private AddressVO addressVo;

  @OneToMany(
      cascade = {CascadeType.PERSIST, CascadeType.MERGE},
      fetch = FetchType.LAZY
  )
  @JoinColumn(name = "order_id", nullable = false)
  private List<OrderProduct> orderProducts = new ArrayList<>();

  public static Order create(UUID producerCompanyId,
      UUID receiverCompanyId,
      AddressVO addressVo,
      String request,
      List<OrderProduct> orderProducts) {
    validateCompanyId(producerCompanyId);
    validateCompanyId(receiverCompanyId);
    validateOrderProducts(orderProducts);

    Order order = new Order();
    order.producerCompanyId = producerCompanyId;
    order.receiverCompanyId = receiverCompanyId;
    order.addressVo = addressVo;
    order.request = request;
    order.orderProducts = orderProducts;
    order.totalPrice = TotalPrice.create(order.calculateTotalPrice());

    return order;
  }

  public void softDelete(UUID deletedBy) {
    super.softDelete(deletedBy);

    this.orderProducts.forEach(op -> op.softDelete(deletedBy));
  }

  public void updateStatus(OrderStatus newStatus) {
    validateStatusTransition(this.status, newStatus);
    this.status = newStatus;
  }

  private int calculateTotalPrice() {
    return orderProducts.stream()
        .reduce(
            0,
            (acc, op) -> {
              int current = Math.multiplyExact(
                  op.getSnapshot().getUnitPrice(),
                  op.getSnapshot().getQuantity()
              );
              return Math.addExact(acc, current);
            },
            Integer::sum
        );
  }

  private static void validateOrderProducts(List<OrderProduct> orderProducts) {
    if (orderProducts == null || orderProducts.isEmpty())
      throw new BaseException(OrderDomainErrorCode.EMPTY_ORDER_PRODUCT);
  }

  private static void validateCompanyId(UUID companyId) {
    if (companyId == null)
      throw new BaseException(OrderDomainErrorCode.INVALID_COMPANY_ID);
  }

  private static void validateStatusTransition(OrderStatus current, OrderStatus newStatus) {
    if (!current.canTransitionTo(newStatus))
      throw new BaseException(OrderDomainErrorCode.INVALID_STATUS_TRANSITION);
  }
}
