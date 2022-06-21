package cashfree.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderNotificationDetails {

    @JsonProperty("orderId")
    String orderId;

    @JsonProperty("referenceId")
    String referenceId;

    @JsonProperty("orderAmount")
    String orderAmount;

    @JsonProperty("txStatus")
    String orderStatus;

    @JsonProperty("txMsg")
    String message;

    @JsonProperty("txTime")
    Date transactionTime;

    @JsonProperty("paymentMode")
    String paymentMode;
}
