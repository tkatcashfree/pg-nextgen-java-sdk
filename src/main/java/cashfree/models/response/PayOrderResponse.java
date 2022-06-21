package cashfree.models.response;

import cashfree.models.PayOrderData;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayOrderResponse {

    @JsonProperty("payment_method")
    String paymentMethod;

    @JsonProperty("channel")
    String channel;

    @JsonProperty("action")
    String action;

    @JsonProperty("data")
    PayOrderData payOrderData;

    @JsonProperty("cf_payment_id")
    String cfPaymentId;
}
