package cashfree.models;

import cashfree.validation.NotAllowedCurrenyCodes;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Currency;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName("paypal")
public class Paypal implements PaymentMethod {

    @NonNull
    @JsonProperty("channel")
    final String channel = "link";

    @NonNull
    @JsonProperty("currency")
    @NotAllowedCurrenyCodes(notAllowedCodes = {"INR"}, message = "Currency not supported for Paypal method")
    Currency currency;

}
