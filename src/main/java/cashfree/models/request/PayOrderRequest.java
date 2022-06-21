package cashfree.models.request;

import cashfree.models.PaymentMethod;
import cashfree.net.http.HttpHeaders;
import cashfree.transformers.deserializers.NestedPaymentMethodDeserializer;
import cashfree.transformers.deserializers.NestedUrlDeserializer;
import cashfree.transformers.serializers.NestedPaymentMethodSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class PayOrderRequest {

    @NonNull
    @JsonProperty("order_token")
    String orderToken;

    @NonNull
    @JsonProperty("payment_method")
    @JsonDeserialize(using = NestedPaymentMethodDeserializer.class)
    @JsonSerialize(using = NestedPaymentMethodSerializer.class)
    PaymentMethod paymentMethod;

    @JsonProperty("save_payment_method")
    @Builder.Default
    Boolean savePaymentMethod = false;

    @JsonIgnore
    HttpHeaders userControlledHttpHeaders;
}
