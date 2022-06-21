package cashfree.models.response;

import cashfree.models.CustomerDetails;
import cashfree.models.OrderMetaData;
import cashfree.models.OrderSplit;
import cashfree.transformers.deserializers.NestedUrlDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonKey;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.util.Map;
import lombok.*;

import java.net.URL;
import java.util.Currency;
import java.util.Date;
import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetOrderResponse {

    @JsonProperty("cf_order_id")
    String cfOrderId;

    @JsonProperty("order_id")
    String orderId;

    @JsonProperty("customer_details")
    CustomerDetails customerDetails;

    @JsonProperty("entity")
    String cfEntity;

    @JsonProperty("order_amount")
    BigDecimal orderAmount;

    @JsonProperty("order_currency")
    Currency currency;

    @JsonProperty("order_expiry_time")
    Date orderExpiryTime;

    @JsonProperty("created_at")
    Date createAt;

    @JsonProperty("order_meta")
    OrderMetaData orderMetaData;

    @JsonProperty("order_splits")
    List<OrderSplit> orderSplits;

    @JsonProperty("order_note")
    String orderNote;

    @JsonProperty("order_status")
    String orderStatus;

    @JsonProperty("order_tags")
    Map<String, String> orderTags;

    @JsonProperty("order_token")
    String orderToken;

    @JsonProperty("payment_link")
    URL paymentLink;


    @JsonProperty("payments")
    @JsonDeserialize(using = NestedUrlDeserializer.class)
    URL payments;

    @JsonProperty("refunds")
    @JsonDeserialize(using = NestedUrlDeserializer.class)
    URL refunds;

    @JsonProperty("settlements")
    @JsonDeserialize(using = NestedUrlDeserializer.class)
    URL settlements;
}
