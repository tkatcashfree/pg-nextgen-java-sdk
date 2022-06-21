package cashfree.validation;

import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class OnlyNFieldsNullValidator
        implements ConstraintValidator<OnlyNFieldsNull, Object> {

    int nullCount;
    private String[] fieldNames;

    @Override
    public void initialize(OnlyNFieldsNull annotation) {
        fieldNames          = annotation.fieldNames();
        nullCount    = annotation.nullCount();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext ctx) {

        if (value == null) {
            return true;
        }
        AtomicInteger nullCountPresent = new AtomicInteger();
        Arrays.stream(fieldNames).forEach(
                fieldName -> {
                    try {
                        if (BeanUtils.getProperty(value, fieldName) == null) {
                            nullCountPresent.getAndIncrement();
                        }
                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
                        throw new RuntimeException(ex);
                    }
                }
        );

        if (nullCountPresent.get() != nullCount) {
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate())
                    .addConstraintViolation();
            return false;
        }

        return true;
    }

}
