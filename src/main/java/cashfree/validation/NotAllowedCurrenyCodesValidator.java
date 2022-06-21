package cashfree.validation;

import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Currency;
import java.util.concurrent.atomic.AtomicInteger;

public class NotAllowedCurrenyCodesValidator
        implements ConstraintValidator<NotAllowedCurrenyCodes, Object> {

    private String[] notAllowedCodes;

    @Override
    public void initialize(NotAllowedCurrenyCodes annotation) {
        notAllowedCodes          = annotation.notAllowedCodes();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext ctx) throws RuntimeException {

        if (value == null) {
            return true;
        }

        Currency currencyValue = null;
        if(value instanceof Currency) {
            currencyValue = (Currency) value;
        } else {
            throw new RuntimeException("Annotation should be used on type Currency");
        }

        if (Arrays.asList(notAllowedCodes).contains(currencyValue.getCurrencyCode())) {
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate())
                    .addConstraintViolation();
            return false;
        }

        return true;
    }

}
