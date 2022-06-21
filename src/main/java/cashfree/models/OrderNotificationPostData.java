package cashfree.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderNotificationPostData {

    @JsonProperty("orderDetails")
    OrderNotificationDetails orderNotificationDetails;

    @JsonProperty("signature")
    String signature;
}
