package cashfree.exception;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvalidRequestException extends CashfreeException {

    @JsonProperty("message")
    String cashfreeErrorMessage;

    @JsonProperty("code")
    String cashfreeErrorCode;

    @JsonProperty("type")
    String cashfreeErrorType;

    int httpErrorCode;

    public InvalidRequestException(String cashfreeErrorCode,
                             String cashfreeErrorMessage,
                             String cashfreeErrorType,
                             int httpErrorCode,
                             Throwable cause) {
        super(cashfreeErrorMessage, cause);
        this.cashfreeErrorCode = cashfreeErrorCode;
        this.cashfreeErrorMessage = cashfreeErrorMessage;
        this.cashfreeErrorType = cashfreeErrorType;
        this.httpErrorCode = httpErrorCode;
    }
}
