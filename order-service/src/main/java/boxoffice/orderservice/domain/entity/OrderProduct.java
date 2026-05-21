package boxoffice.orderservice.domain.entity;

import com.boxoffice.common.entity.BaseEntityNotDeleted;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_order_products")
public class OrderProduct extends BaseEntityNotDeleted {



}
