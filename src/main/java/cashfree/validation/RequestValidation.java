package cashfree.validation;

import static cashfree.validation.ValidationConstants.SUPPORTED_PAYMENT_METHODS;

import cashfree.exception.CashfreeValidationException;
import cashfree.models.*;

import cashfree.models.request.CreateOrderRequest;
import cashfree.models.request.PayOrderRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RequestValidation {

    public static void validateCreateOrderRequest(CreateOrderRequest request) throws CashfreeValidationException {
        List<ModelValidationError> errors = new ArrayList<>();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<CreateOrderRequest>> orderViolations = validator.validate(request);
        addViolationToErrors(orderViolations, errors);

        Set<ConstraintViolation<CustomerDetails>> customerDetailsViolations = validator.validate(request.getCustomerDetails());
        addViolationToErrors(customerDetailsViolations, errors);

        if(request.getOrderSplits() != null) {
            request.getOrderSplits().stream().forEach(
                    orderSplit -> {
                        if (orderSplit.getAmount() != null && orderSplit.getPercentage() != null) {
                            errors.add(
                                    ModelValidationError.builder()
                                            .attribute("")
                                            .message("both amount and percentage can't be provided in a split field")
                                            .T(OrderSplit.class)
                                            .classPath(OrderSplit.class.toString())
                                            .build()
                            );
                        }
                        if (orderSplit.getAmount() == null && orderSplit.getPercentage() == null) {
                            errors.add(
                                    ModelValidationError.builder()
                                            .attribute("")
                                            .message("both amount and percentage can't be null in a split field")
                                            .T(OrderSplit.class)
                                            .classPath(OrderSplit.class.toString())
                                            .build()
                            );
                        }
                        Set<ConstraintViolation<OrderSplit>> splitViolations = validator.validate(orderSplit);
                        addViolationToErrors(splitViolations, errors);
                    }
            );
        }


        if(request.getOrderInvoice() != null) {
            Set<ConstraintViolation<OrderInvoice>> orderInvoiceViolations = validator.validate(request.getOrderInvoice());
            addViolationToErrors(orderInvoiceViolations, errors);
        }

        if(request.getOrderMetaData() != null) {
            Set<ConstraintViolation<OrderMetaData>> orderMetaViolations = validator.validate(request.getOrderMetaData());
            addViolationToErrors(orderMetaViolations, errors);
            if(request.getOrderMetaData().getPaymentMethods() != null) {
                String paymentMethodString = request.getOrderMetaData().getPaymentMethods();
                if(paymentMethodString.length() > 0) {
                    String[] paymentMethods = paymentMethodString.split(",");
                    for(String paymentMethod: paymentMethods) {
                        if(!SUPPORTED_PAYMENT_METHODS.containsKey(paymentMethod) || !SUPPORTED_PAYMENT_METHODS.get(paymentMethod)) {
                            errors.add(
                                ModelValidationError.builder()
                                    .attribute("")
                                    .message("payment method should be a comma separated list of allowed payment methods")
                                    .T(OrderMetaData.class)
                                    .classPath(OrderMetaData.class.toString())
                                    .build()
                            );
                        }
                    }
                }
            }
        }

        if(errors.size() > 0) {
            throw new CashfreeValidationException(errors, "validation errors found in request");
        }
        return;
    }

    public static void validatePayOrderRequest(PayOrderRequest request) throws CashfreeValidationException {
        List<ModelValidationError> errors = new ArrayList<>();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<PayOrderRequest>> orderViolations = validator.validate(request);
        addViolationToErrors(orderViolations, errors);

        Set<ConstraintViolation<PaymentMethod>> paymentMethodViolations = validator.validate(request.getPaymentMethod());
        addViolationToErrors(paymentMethodViolations, errors);

        if(errors.size() > 0) {
            throw new CashfreeValidationException(errors, "validation errors found in request");
        }
        return;
    }

    public static <T> void addViolationToErrors(Set<ConstraintViolation<T>> violations, List<ModelValidationError> errors) {
        violations.forEach(
                violation -> {
                    errors.add(
                            ModelValidationError.builder()
                                    .attribute(violation.getPropertyPath().toString())
                                    .classPath(violation.getRootBeanClass().toString())
                                    .message(violation.getMessage())
                                    .T(violation.getRootBeanClass())
                                    .build()
                    );
                }
        );
    }

}
