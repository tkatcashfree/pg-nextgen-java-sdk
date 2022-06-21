package cashfree.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Pattern;
import lombok.*;

import javax.validation.constraints.Size;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetails {

    @NonNull
    @JsonProperty("customer_id")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "invalid customer id. can only contain alphabets, numbers, _ and -")
    @Size(max = 50)
    String customerId;

    @JsonProperty("customer_email")
    @Pattern(regexp = "(\\A[\\w+\\-.]+@[a-z\\d\\-]+(\\.[a-z\\-]+)*\\.[a-z]+\\z|^$)", message = "please assign a valid email")
    String customerMail;

    @NonNull
    @JsonProperty("customer_phone")
    @Pattern(regexp = "^(\\+(?:[0-9]\\x20?){6,14}[0-9]|[6-9][0-9]{9})$", message = "provide valid indian or international phone number")
    String customerPhone;

    @JsonProperty("customer_name")
    @Pattern(regexp = "(^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$|^$)", message = "enter valid person name")
    String customerName;

    @JsonProperty("customer_bank_ifsc")
    @Pattern(regexp = "(^[A-Z]{4}0[A-Z0-9]{6}$|^$)", message = "assign valid IFSC code")
    String customerBankIfsc;

    @JsonProperty("customer_bank_account_number")
    @Pattern(regexp = "(^[A-Za-z0-9]+$|^$)", message = "account number must be alphanumeric")
    String customerBankAccountNumber;

    @JsonProperty("customer_bank_code")
    int customerBankCode;
}
