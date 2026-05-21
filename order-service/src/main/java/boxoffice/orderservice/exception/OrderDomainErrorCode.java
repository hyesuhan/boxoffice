package boxoffice.orderservice.exception;

import com.boxoffice.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum OrderDomainErrorCode implements ErrorCode {
  INVALID_COMPANY_ID(HttpStatus.BAD_REQUEST, "ORDER_DOMAIN_001", "업체 ID가 올바르지 않습니다."),
  INVALID_ORDER_PRODUCT(HttpStatus.BAD_REQUEST, "ORDER_DOMAIN_002", "상품명/가격/수량이 형식에 맞지 않습니다."),
  INVALID_PRODUCT_ID(HttpStatus.BAD_REQUEST, "ORDER_DOMAIN_003", "상품 ID가 올바르지 않습니다.")
  ;

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;
}
