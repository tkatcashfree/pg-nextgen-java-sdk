package cashfree.exception;

import cashfree.models.ModelValidationError;
import lombok.Getter;

import java.util.List;

@Getter
public class CashfreeValidationException  extends CashfreeException {

    List<ModelValidationError> validationErrors;

    String message;

    public CashfreeValidationException(List<ModelValidationError> errors,
                               String message) {
        super(message);
        this.cashfreeErrorMessage = cashfreeErrorMessage;
        this.validationErrors = errors;
        this.message = message;
    }
}
