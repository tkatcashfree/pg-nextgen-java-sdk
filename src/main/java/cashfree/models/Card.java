package cashfree.models;


import cashfree.validation.NotNullIfAnotherFieldIsNull;
import cashfree.validation.OnlyNFieldsNull;
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
@JsonTypeName("card")
@NotNullIfAnotherFieldIsNull(
        fieldName = "cardAlias",
        dependFieldName = "expiryYear")
@NotNullIfAnotherFieldIsNull(
        fieldName = "cardAlias",
        dependFieldName = "expiryMonth")
@NotNullIfAnotherFieldIsNull(
        fieldName = "cardAlias",
        dependFieldName = "cardCvv")
@NotNullIfAnotherFieldIsNull(
        fieldName = "cardAlias",
        dependFieldName = "cardNumber")
@NotNullIfAnotherFieldIsNull(
        fieldName = "cardNumber",
        dependFieldName = "cardAlias")
@OnlyNFieldsNull(nullCount = 1, fieldNames = {"cardNumber", "cardAlias"},
        message = "either card number or card alias should be provided")
public class Card implements PaymentMethod {

    @NonNull
    @JsonProperty("channel")
    final String channel = "link";

    @JsonProperty("card_number")
    String cardNumber;

    @JsonProperty("card_holder_name")
    String cardHolderName;

    @JsonProperty("card_expiry_mm")
    @Size(min = 2, max = 2)
    @Pattern(regexp = "^[0-9]*$", message = "invalid format for card expiry month. Use 2 digit notation in string format.")
    String expiryMonth;

    @JsonProperty("card_expiry_yy")
    @Size(min = 2, max = 2)
    @Pattern(regexp = "^[0-9]*$", message = "invalid format for card expiry year. Use 2 digit notation in string format.")
    String expiryYear;

    @JsonProperty("card_cvv")
    @Size(min = 3, max = 3, message = "card cvv should be a 3 digit string")
    @Pattern(regexp = "^[0-9]*$", message = "card cvv should be a 3 digit string")
    String cardCvv;

    @JsonProperty("card_alias")
    String cardAlias;

}
