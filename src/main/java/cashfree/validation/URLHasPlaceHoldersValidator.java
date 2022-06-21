package cashfree.validation;

import java.net.URL;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class URLHasPlaceHoldersValidator
    implements ConstraintValidator<URLHasPlaceHolders, Object> {

  private String[] placeHolders;

  @Override
  public void initialize(URLHasPlaceHolders annotation) {
    placeHolders = annotation.placeHolders();
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext ctx) throws RuntimeException {

    if (value == null) {
      return true;
    }

    URL urlToBeValidated = null;
    if(value instanceof URL) {
      urlToBeValidated = (URL) value;
    } else {
      throw new RuntimeException("Annotation should be used on type URL");
    }

    for(String placeHolder: placeHolders) {
      if(!urlToBeValidated.toString().contains(placeHolder)) {
        ctx.disableDefaultConstraintViolation();
        ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate())
            .addConstraintViolation();
        return false;
      }
    }

    return true;
  }

}