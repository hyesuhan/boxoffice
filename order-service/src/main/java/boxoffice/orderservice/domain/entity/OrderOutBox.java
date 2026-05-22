package boxoffice.orderservice.domain.entity;

import boxoffice.orderservice.domain.enums.OutBoxEventType;
import boxoffice.orderservice.domain.enums.OutBoxStatus;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_outbox_event")
public class OrderOutBox {

  private static final TimeBasedEpochGenerator UUID_GENERATOR =
      Generators.timeBasedEpochGenerator();

  @Id
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @Column(name = "aggregate_id", nullable = false)
  private UUID aggregateId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 50)
  private OutBoxEventType eventType;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String payload;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private OutBoxStatus status;

  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column
  private LocalDateTime publishedAt;

  public static OrderOutBox create(
      UUID aggregateId,
      OutBoxEventType eventType,
      String payload
  ) {
    OrderOutBox outBox = new OrderOutBox();
    outBox.aggregateId = aggregateId;
    outBox.eventType = eventType;
    outBox.eventType = eventType;
    outBox.payload = payload;
    outBox.status = OutBoxStatus.PENDING;
    outBox.createdAt = LocalDateTime.now();
    return outBox;
  }

  public void markPublished() {
    this.status = OutBoxStatus.PUBLISHED;
    this.publishedAt = LocalDateTime.now();
  }

  public void markFailed() {
    this.status = OutBoxStatus.FAILED;
  }

  @PrePersist
  protected void prePersist() {
    if (id == null) {
      id = UUID_GENERATOR.generate();
    }
  }
}
