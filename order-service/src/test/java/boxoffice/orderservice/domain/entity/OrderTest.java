package boxoffice.orderservice.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import boxoffice.orderservice.domain.enums.OrderStatus;
import boxoffice.orderservice.fixture.OrderFixture;
import com.boxoffice.common.exception.BaseException;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class OrderTest {

    // ───────────────── 생성 성공 ─────────────────

    @Nested
    @DisplayName("주문 생성")
    class Create {

        @Test
        @DisplayName("성공 — 초기 상태는 PENDING")
        void create_success_initialStatusIsPending() {
            Order order = OrderFixture.pendingOrder();

            assertThat(order.getStatus()).isEqualTo(OrderStatus.PENDING);
        }

        @Test
        @DisplayName("성공 — totalPrice는 (단가 × 수량) 합산")
        void create_success_totalPriceCalculated() {
            // 상품A: 1000 × 3 = 3000, 상품B: 2000 × 2 = 4000 → 합계 7000
            List<OrderProduct> products = OrderFixture.twoProducts();
            Order order = Order.create(
                UUID.randomUUID(), UUID.randomUUID(),
                UUID.randomUUID(), UUID.randomUUID(),
                "요청사항", products
            );

            assertThat(order.getTotalPrice().getValue()).isEqualTo(7000);
        }

        @Test
        @DisplayName("성공 — request 필드 저장")
        void create_success_requestSaved() {
            Order order = OrderFixture.pendingOrder();

            assertThat(order.getRequest()).isEqualTo("납기 요청");
        }

        @Test
        @DisplayName("성공 — 상품 목록 저장")
        void create_success_productsStored() {
            List<OrderProduct> products = OrderFixture.twoProducts();
            Order order = Order.create(
                UUID.randomUUID(), UUID.randomUUID(),
                UUID.randomUUID(), UUID.randomUUID(),
                null, products
            );

            assertThat(order.getOrderProducts()).hasSize(2);
        }

        // ───────────────── 생성 실패 ─────────────────

        @Test
        @DisplayName("실패 — 상품 목록 비어있음")
        void create_fail_emptyProducts() {
            assertThatThrownBy(() ->
                Order.create(
                    UUID.randomUUID(), UUID.randomUUID(),
                    UUID.randomUUID(), UUID.randomUUID(),
                    "요청사항", List.of()
                )
            ).isInstanceOf(BaseException.class);
        }

        @Test
        @DisplayName("실패 — 상품 목록 null")
        void create_fail_nullProducts() {
            assertThatThrownBy(() ->
                Order.create(
                    UUID.randomUUID(), UUID.randomUUID(),
                    UUID.randomUUID(), UUID.randomUUID(),
                    "요청사항", null
                )
            ).isInstanceOf(BaseException.class);
        }

        @Test
        @DisplayName("실패 — 생산업체 ID null")
        void create_fail_nullProducerCompanyId() {
            assertThatThrownBy(() ->
                Order.create(
                    null, UUID.randomUUID(),
                    UUID.randomUUID(), UUID.randomUUID(),
                    "요청사항", List.of(OrderFixture.singleProduct())
                )
            ).isInstanceOf(BaseException.class);
        }

        @Test
        @DisplayName("실패 — 수령업체 ID null")
        void create_fail_nullReceiverCompanyId() {
            assertThatThrownBy(() ->
                Order.create(
                    UUID.randomUUID(), null,
                    UUID.randomUUID(), UUID.randomUUID(),
                    "요청사항", List.of(OrderFixture.singleProduct())
                )
            ).isInstanceOf(BaseException.class);
        }
    }

    // ───────────────── 상태 전이 ─────────────────

    @Nested
    @DisplayName("상태 전이")
    class StatusTransition {

        @Test
        @DisplayName("성공 — PENDING → CONFIRMED")
        void updateStatus_pendingToConfirmed() {
            Order order = OrderFixture.pendingOrder();

            order.updateStatus(OrderStatus.CONFIRMED);

            assertThat(order.getStatus()).isEqualTo(OrderStatus.CONFIRMED);
        }

        @Test
        @DisplayName("성공 — PENDING → CANCELLED")
        void updateStatus_pendingToCancelled() {
            Order order = OrderFixture.pendingOrder();

            order.updateStatus(OrderStatus.CANCELLED);

            assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCELLED);
        }

        @Test
        @DisplayName("실패 — CONFIRMED 이후 상태 변경 불가")
        void updateStatus_confirmedToAny_fail() {
            Order order = OrderFixture.pendingOrder();
            order.updateStatus(OrderStatus.CONFIRMED);

            assertThatThrownBy(() -> order.updateStatus(OrderStatus.CANCELLED))
                .isInstanceOf(BaseException.class);
        }

        @Test
        @DisplayName("실패 — CANCELLED 이후 상태 변경 불가")
        void updateStatus_cancelledToAny_fail() {
            Order order = OrderFixture.pendingOrder();
            order.updateStatus(OrderStatus.CANCELLED);

            assertThatThrownBy(() -> order.updateStatus(OrderStatus.CONFIRMED))
                .isInstanceOf(BaseException.class);
        }

        @Test
        @DisplayName("실패 — PENDING → PENDING (동일 상태 전이 불가)")
        void updateStatus_sameToPending_fail() {
            Order order = OrderFixture.pendingOrder();

            assertThatThrownBy(() -> order.updateStatus(OrderStatus.PENDING))
                .isInstanceOf(BaseException.class);
        }
    }

    // ───────────────── 배송 할당 ─────────────────

    @Nested
    @DisplayName("배송 할당")
    class AssignDelivery {

        @Test
        @DisplayName("성공 — deliveryId, hub 정보 저장")
        void assignDelivery_success() {
            Order order = OrderFixture.pendingOrder();
            UUID deliveryId = UUID.randomUUID();
            UUID originHub = UUID.randomUUID();
            UUID destHub = UUID.randomUUID();

            order.assignDelivery(deliveryId, originHub, destHub);

            assertThat(order.getDeliveryId()).isEqualTo(deliveryId);
            assertThat(order.getOriginHubId()).isEqualTo(originHub);
            assertThat(order.getDestinationHubId()).isEqualTo(destHub);
        }

        @Test
        @DisplayName("실패 — deliveryId null")
        void assignDelivery_fail_nullDeliveryId() {
            Order order = OrderFixture.pendingOrder();

            assertThatThrownBy(() ->
                order.assignDelivery(null, UUID.randomUUID(), UUID.randomUUID())
            ).isInstanceOf(BaseException.class);
        }

        @Test
        @DisplayName("실패 — originHubId null")
        void assignDelivery_fail_nullOriginHubId() {
            Order order = OrderFixture.pendingOrder();

            assertThatThrownBy(() ->
                order.assignDelivery(UUID.randomUUID(), null, UUID.randomUUID())
            ).isInstanceOf(BaseException.class);
        }

        @Test
        @DisplayName("실패 — destinationHubId null")
        void assignDelivery_fail_nullDestinationHubId() {
            Order order = OrderFixture.pendingOrder();

            assertThatThrownBy(() ->
                order.assignDelivery(UUID.randomUUID(), UUID.randomUUID(), null)
            ).isInstanceOf(BaseException.class);
        }
    }
}
