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
@JsonTypeName("upi")
public class AppPayment implements PaymentMethod {

    @NonNull
    @JsonProperty("channel")
    AppChannel channel;

    @NonNull
    @JsonProperty("phone")
    @Size(min = 10, max = 10)
    @Pattern(regexp = "^[0-9]*$", message = "invalid phone number. Provide a ten digit string")
    String phone;

}
