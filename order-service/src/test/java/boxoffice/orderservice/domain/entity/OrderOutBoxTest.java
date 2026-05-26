package boxoffice.orderservice.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import boxoffice.orderservice.domain.enums.OutBoxEventType;
import boxoffice.orderservice.domain.enums.OutBoxStatus;
import java.lang.reflect.Field;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderOutBoxTest {

    // ───────────────── 생성 성공 ─────────────────

    @Test
    @DisplayName("OutBox 생성 성공 — 예외 없음")
    void create_success_noException() {
        assertThatCode(() ->
            OrderOutBox.create(UUID.randomUUID(), OutBoxEventType.ORDER_CREATED, "{}")
        ).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("OutBox 생성 성공 — 초기 상태는 PENDING")
    void create_success_initialStatusIsPending() throws Exception {
        OrderOutBox outBox = OrderOutBox.create(
            UUID.randomUUID(), OutBoxEventType.ORDER_CREATED, "{}"
        );

        assertThat(getField(outBox, "status")).isEqualTo(OutBoxStatus.PENDING);
    }

    @Test
    @DisplayName("OutBox 생성 성공 — payload 저장")
    void create_success_payloadStored() throws Exception {
        String payload = "{\"orderId\":\"test\"}";
        OrderOutBox outBox = OrderOutBox.create(
            UUID.randomUUID(), OutBoxEventType.ORDER_CREATED, payload
        );

        assertThat(getField(outBox, "payload")).isEqualTo(payload);
    }

    // ───────────────── 상태 변경 ─────────────────

    @Test
    @DisplayName("markPublished — 상태가 PUBLISHED로 변경")
    void markPublished_statusBecomesPublished() throws Exception {
        OrderOutBox outBox = OrderOutBox.create(
            UUID.randomUUID(), OutBoxEventType.ORDER_CREATED, "{}"
        );

        outBox.markPublished();

        assertThat(getField(outBox, "status")).isEqualTo(OutBoxStatus.PUBLISHED);
    }

    @Test
    @DisplayName("markPublished — publishedAt 설정됨")
    void markPublished_publishedAtIsSet() throws Exception {
        OrderOutBox outBox = OrderOutBox.create(
            UUID.randomUUID(), OutBoxEventType.ORDER_CREATED, "{}"
        );

        outBox.markPublished();

        assertThat(getField(outBox, "publishedAt")).isNotNull();
    }

    @Test
    @DisplayName("markFailed — 상태가 FAILED로 변경")
    void markFailed_statusBecomesFailed() throws Exception {
        OrderOutBox outBox = OrderOutBox.create(
            UUID.randomUUID(), OutBoxEventType.ORDER_CREATED, "{}"
        );

        outBox.markFailed();

        assertThat(getField(outBox, "status")).isEqualTo(OutBoxStatus.FAILED);
    }

    // @Getter 없는 클래스의 private 필드 접근 헬퍼
    private Object getField(Object target, String fieldName) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(target);
    }
}
