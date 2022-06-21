package cashfree.models.request;

import cashfree.net.http.HttpHeaders;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.*;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class GetOrderRequest {

    @NonNull
    @JsonProperty("order_id")
    @Size(min = 1, max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "invalid order id. can only contain alphabets, numbers, _ and -")
    String orderID;

    @JsonIgnore
    HttpHeaders userControlledHttpHeaders;
}
