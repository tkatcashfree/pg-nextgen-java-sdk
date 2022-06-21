package cashfree.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName("emi")
public class CardEmi implements PaymentMethod {

    @NonNull
    @JsonProperty("channel")
    final String channel = "link";

    @NonNull
    @JsonProperty("card_number")
    String cardNumber;

    @NonNull
    @JsonProperty("card_expiry_mm")
    @Size(min = 2, max = 2)
    @Pattern(regexp = "^[0-9]*$", message = "invalid format for card expiry month. Use 2 digit notation in string format.")
    String expiryMonth;

    @NonNull
    @JsonProperty("card_expiry_yy")
    @Size(min = 2, max = 2)
    @Pattern(regexp = "^[0-9]*$", message = "invalid format for card expiry year. Use 2 digit notation in string format.")
    String expiryYear;

    @JsonProperty("card_cvv")
    @Size(min = 3, max = 3, message = "card cvv should be a 3 digit string")
    @Pattern(regexp = "^[0-9]*$", message = "card cvv should be a 3 digit string")
    String cardCvv;

    @NonNull
    @JsonProperty("card_bank_name")
    String cardBankName;

    @NonNull
    @JsonProperty("emi_tenure")
    int emiTenure;

}
