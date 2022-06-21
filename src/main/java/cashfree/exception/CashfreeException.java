package cashfree.exception;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CashfreeException extends Exception {

    @JsonProperty("message")
    String cashfreeErrorMessage;

    public CashfreeException(String cashfreeErrorMessage,
                             Throwable cause) {
        super(cashfreeErrorMessage, cause);
        this.cashfreeErrorMessage = cashfreeErrorMessage;
    }
}
