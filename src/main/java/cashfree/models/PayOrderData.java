package cashfree.models;

import cashfree.transformers.deserializers.NestedPaymentMethodDeserializer;
import cashfree.transformers.deserializers.PayOrderDataPayloadDeserializer;
import cashfree.transformers.serializers.NestedPaymentMethodSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.util.Map;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class PayOrderData {

    @JsonProperty("url")
    String url;

    @JsonProperty("payload")
    @JsonDeserialize(using = PayOrderDataPayloadDeserializer.class)
    PayOrderDataPayload payload;

    @JsonProperty("content_type")
    String contentType;

    @JsonProperty("method")
    String method;
}
