package boxoffice.orderservice.exception;

import com.boxoffice.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties.Http;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum OrderErrorCode implements ErrorCode {
  ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER_001", "주문을 찾을 수 없습니다."),
  INVALID_ADDRESS(HttpStatus.BAD_REQUEST, "ORDER_002", "유효하지 않은 배송 주소입니다."),
  INVALID_PRICE(HttpStatus.BAD_REQUEST, "ORDER_003", "유효하지 않은 주문 금액입니다."),
  SAME_COMPANY_ORDER(HttpStatus.BAD_REQUEST, "ORDER_004", "본인 회사의 제품은 주문할 수 없습니다."),
  MISSING_USER_CONTEXT(HttpStatus.BAD_REQUEST, "ORDER_005", "해당 유저를 확인할 수 없습니다.")
  ;

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;

}
