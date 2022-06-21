package cashfree.models.request;

import cashfree.models.CustomerDetails;
import cashfree.models.OrderInvoice;
import cashfree.models.OrderMetaData;
import cashfree.models.OrderSplit;
import cashfree.net.http.HttpHeaders;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {

    @JsonProperty("order_id")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "invalid order id. can only contain alphabets, numbers, _ and -")
    @Size(min = 1, max = 50)
    String orderId;

    @NonNull
    @JsonProperty("order_amount")
    @DecimalMin(value = "1")
    @Digits(integer = 100, fraction = 2, message = "order amount can have at max 2 decimal places")
    BigDecimal orderAmount;

    @NonNull
    @JsonProperty("order_currency")
    Currency currency;

    @NonNull
    @JsonProperty("customer_details")
    CustomerDetails customerDetails;

    @JsonProperty("order_meta")
    OrderMetaData orderMetaData;

    @JsonProperty("order_expiry_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    Date orderExpiryTime;

    @JsonProperty("order_note")
    @Size(max = 250)
    @Pattern(regexp = "\\A\\p{ASCII}*\\z", message = "order note should contain only ascii characters")
    String orderNote;

    @JsonProperty("order_tags")
    @Size(min = 1, max = 15, message = "Order tags must have more than zero and less than 15 entries")
    Map<@Size(max = 64, message = "order tag key to be less than 64 characters") String,
        @Size(max = 256, message = "order tage value to be less than 256 characters") String> orderTags;

    @JsonIgnore
    OrderInvoice orderInvoice;

    @JsonProperty("order_splits")
    List<OrderSplit> orderSplits;

    @JsonIgnore
    HttpHeaders userControlledHttpHeaders;
}
