package cashfree.transformers.deserializers;

import cashfree.models.Card;
import cashfree.models.PaymentMethod;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class NestedPaymentMethodDeserializer extends StdDeserializer<PaymentMethod> {

    public NestedPaymentMethodDeserializer() {
        this(null);
    }

    public NestedPaymentMethodDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public PaymentMethod deserialize(JsonParser jp, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        Map.Entry<String, JsonNode> paymentMethod = node.fields().next();
        String key = paymentMethod.getKey();
        JsonNode paymentMethodNode = paymentMethod.getValue();
        switch (key) {
            case "card":
            default:
                String cardHolderName = paymentMethodNode.has("card_holder_name")?
                        paymentMethodNode.get("card_holder_name").asText() :
                        null;
                return Card.builder()
                        .cardCvv(paymentMethodNode.get("card_cvv").asText())
                        .cardHolderName(cardHolderName)
                        .cardNumber(paymentMethodNode.get("card_number").asText())
                        .expiryMonth(paymentMethodNode.get("card_expiry_mm").asText())
                        .expiryYear(paymentMethodNode.get("card_expiry_yy").asText())
                        .build();
        }
    }
}