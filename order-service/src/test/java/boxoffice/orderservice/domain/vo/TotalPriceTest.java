package boxoffice.orderservice.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.boxoffice.common.exception.BaseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TotalPriceTest {

    // ───────────────── 생성 성공 ─────────────────

    @Test
    @DisplayName("총 금액 생성 성공 — 양수")
    void create_success() {
        TotalPrice price = TotalPrice.create(5000);

        assertThat(price.getValue()).isEqualTo(5000);
    }

    @Test
    @DisplayName("총 금액 생성 성공 — 0원 허용")
    void create_success_zero() {
        TotalPrice price = TotalPrice.create(0);

        assertThat(price.getValue()).isZero();
    }

    // ───────────────── 생성 실패 ─────────────────

    @Test
    @DisplayName("총 금액 생성 실패 — null")
    void create_fail_null() {
        assertThatThrownBy(() ->
            TotalPrice.create(null)
        ).isInstanceOf(BaseException.class);
    }

    @Test
    @DisplayName("총 금액 생성 실패 — 음수")
    void create_fail_negative() {
        assertThatThrownBy(() ->
            TotalPrice.create(-1)
        ).isInstanceOf(BaseException.class);
    }

    // ───────────────── 덧셈 ─────────────────

    @Test
    @DisplayName("총 금액 합산 성공")
    void add_success() {
        TotalPrice a = TotalPrice.create(3000);
        TotalPrice b = TotalPrice.create(2000);

        TotalPrice result = a.add(b);

        assertThat(result.getValue()).isEqualTo(5000);
    }

    @Test
    @DisplayName("총 금액 동등성 — 같은 금액은 equal")
    void equality_sameValue() {
        TotalPrice a = TotalPrice.create(1000);
        TotalPrice b = TotalPrice.create(1000);

        assertThat(a).isEqualTo(b);
    }
}
