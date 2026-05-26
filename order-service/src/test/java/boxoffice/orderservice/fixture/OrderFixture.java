package boxoffice.orderservice.fixture;

import boxoffice.orderservice.domain.entity.Order;
import boxoffice.orderservice.domain.entity.OrderProduct;
import java.util.List;
import java.util.UUID;

public class OrderFixture {

    public static final UUID PRODUCER_ID = UUID.randomUUID();
    public static final UUID RECEIVER_ID = UUID.randomUUID();
    public static final UUID ORIGIN_HUB_ID = UUID.randomUUID();
    public static final UUID DEST_HUB_ID = UUID.randomUUID();
    public static final UUID PRODUCT_ID = UUID.randomUUID();

    public static OrderProduct singleProduct() {
        return OrderProduct.create(PRODUCT_ID, "상품A", 1000, 5);
    }

    public static List<OrderProduct> twoProducts() {
        return List.of(
            OrderProduct.create(UUID.randomUUID(), "상품A", 1000, 3),
            OrderProduct.create(UUID.randomUUID(), "상품B", 2000, 2)
        );
    }

    public static Order pendingOrder() {
        return Order.create(PRODUCER_ID, RECEIVER_ID, ORIGIN_HUB_ID, DEST_HUB_ID,
            "납기 요청", List.of(singleProduct()));
    }
}
