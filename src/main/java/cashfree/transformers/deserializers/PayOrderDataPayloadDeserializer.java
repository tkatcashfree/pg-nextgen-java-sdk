package cashfree.transformers.deserializers;

import cashfree.models.PayOrderData;
import cashfree.models.PayOrderDataPayload;
import cashfree.models.UpiLinksPayloadResponse;
import cashfree.models.UpiQrCodePayloadResponse;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.net.URL;

public class PayOrderDataPayloadDeserializer extends StdDeserializer<PayOrderDataPayload> {

    public PayOrderDataPayloadDeserializer() {
        this(null);
    }

    public PayOrderDataPayloadDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public PayOrderDataPayload deserialize(JsonParser jp, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        if(node.get("qrcode") != null) {
            String qrCode = node.get("qrcode").asText();
            return UpiQrCodePayloadResponse.builder().qrCode(qrCode).build();
        } else {
            return UpiLinksPayloadResponse.builder()
                    .bhim(new URL(node.get("bhim").asText()))
                    .defaultUrl(new URL(node.get("default").asText()))
                    .gpay(new URL(node.get("gpay").asText()))
                    .paytm(new URL(node.get("paytm").asText()))
                    .phonepe(new URL(node.get("phonepe").asText()))
                    .build();
        }
    }
}
