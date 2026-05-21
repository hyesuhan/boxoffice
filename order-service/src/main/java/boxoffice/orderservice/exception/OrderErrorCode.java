package boxoffice.orderservice.exception;

import com.boxoffice.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum OrderErrorCode implements ErrorCode {
  ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER_001", "주문을 찾을 수 없습니다."),
  INVALID_ADDRESS(HttpStatus.BAD_REQUEST, "ORDER_002", "유효하지 않은 배송 주소입니다."),
  INVALID_PRICE(HttpStatus.BAD_REQUEST, "ORDER_003", "유효하지 않은 주문 금액입니다."),
  ;

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;

}
