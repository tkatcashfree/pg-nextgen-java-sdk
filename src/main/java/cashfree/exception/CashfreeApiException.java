package cashfree.exception;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CashfreeApiException extends CashfreeException {

    @JsonProperty("message")
    String cashfreeErrorMessage;

    @JsonProperty("code")
    String cashfreeErrorCode;

    @JsonProperty("type")
    String cashfreeErrorType;

    int httpErrorCode;

    public CashfreeApiException(String cashfreeErrorMessage,
                                   String cashfreeErrorCode,
                                   String cashfreeErrorType,
                                   int httpErrorCode,
                                   Throwable cause) {
        super(cashfreeErrorMessage, cause);
        this.cashfreeErrorMessage = cashfreeErrorMessage;
        this.cashfreeErrorCode = cashfreeErrorCode;
        this.cashfreeErrorType = cashfreeErrorType;
        this.httpErrorCode = httpErrorCode;
    }
}
