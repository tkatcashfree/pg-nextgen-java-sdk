package cashfree.exception;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CashfreeUrlException extends CashfreeException {

    @JsonProperty("message")
    String cashfreeErrorMessage;

    public CashfreeUrlException(String cashfreeErrorMessage,
                             Throwable cause) {
        super(cashfreeErrorMessage, cause);
        this.cashfreeErrorMessage = cashfreeErrorMessage;
    }
}
