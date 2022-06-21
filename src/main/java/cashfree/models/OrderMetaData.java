package cashfree.models;

import cashfree.validation.URLHasPlaceHolders;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderMetaData {

    @JsonProperty("return_url")
    @URLHasPlaceHolders(placeHolders = {"{order_id}", "{order_token}"},
        message = "return url must contain placeholders {order_id} and {order_token}")
    URL returnUrl;

    @JsonProperty("notify_url")
    URL notifyUrl;

    @JsonProperty("payment_methods")
    String paymentMethods;

}
