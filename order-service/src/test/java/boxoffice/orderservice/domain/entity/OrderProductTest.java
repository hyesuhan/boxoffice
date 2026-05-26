package boxoffice.orderservice.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.boxoffice.common.exception.BaseException;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderProductTest {

    // ───────────────── 생성 성공 ─────────────────

    @Test
    @DisplayName("주문 상품 생성 성공 — 유효한 입력")
    void create_success() {
        UUID productId = UUID.randomUUID();

        OrderProduct product = OrderProduct.create(productId, "상품A", 1000, 5);

        assertThat(product.getProductId()).isEqualTo(productId);
        assertThat(product.getProductName()).isEqualTo("상품A");
        assertThat(product.getUnitPrice()).isEqualTo(1000);
        assertThat(product.getQuantity()).isEqualTo(5);
    }

    @Test
    @DisplayName("주문 상품 생성 성공 — 단가 0원 허용")
    void create_success_zeroPriceAllowed() {
        OrderProduct product = OrderProduct.create(UUID.randomUUID(), "무료 상품", 0, 1);

        assertThat(product.getUnitPrice()).isZero();
    }

    // ───────────────── 생성 실패 ─────────────────

    @Test
    @DisplayName("주문 상품 생성 실패 — productId null")
    void create_fail_nullProductId() {
        assertThatThrownBy(() ->
            OrderProduct.create(null, "상품A", 1000, 5)
        ).isInstanceOf(BaseException.class);
    }

    @Test
    @DisplayName("주문 상품 생성 실패 — productName null")
    void create_fail_nullProductName() {
        assertThatThrownBy(() ->
            OrderProduct.create(UUID.randomUUID(), null, 1000, 5)
        ).isInstanceOf(BaseException.class);
    }

    @Test
    @DisplayName("주문 상품 생성 실패 — productName 빈 문자열")
    void create_fail_blankProductName() {
        assertThatThrownBy(() ->
            OrderProduct.create(UUID.randomUUID(), "", 1000, 5)
        ).isInstanceOf(BaseException.class);
    }

    @Test
    @DisplayName("주문 상품 생성 실패 — 단가 null")
    void create_fail_nullUnitPrice() {
        assertThatThrownBy(() ->
            OrderProduct.create(UUID.randomUUID(), "상품A", null, 5)
        ).isInstanceOf(BaseException.class);
    }

    @Test
    @DisplayName("주문 상품 생성 실패 — 단가 음수")
    void create_fail_negativeUnitPrice() {
        assertThatThrownBy(() ->
            OrderProduct.create(UUID.randomUUID(), "상품A", -1, 5)
        ).isInstanceOf(BaseException.class);
    }

    @Test
    @DisplayName("주문 상품 생성 실패 — 수량 null")
    void create_fail_nullQuantity() {
        assertThatThrownBy(() ->
            OrderProduct.create(UUID.randomUUID(), "상품A", 1000, null)
        ).isInstanceOf(BaseException.class);
    }

    @Test
    @DisplayName("주문 상품 생성 실패 — 수량 음수")
    void create_fail_negativeQuantity() {
        assertThatThrownBy(() ->
            OrderProduct.create(UUID.randomUUID(), "상품A", 1000, -1)
        ).isInstanceOf(BaseException.class);
    }
}
