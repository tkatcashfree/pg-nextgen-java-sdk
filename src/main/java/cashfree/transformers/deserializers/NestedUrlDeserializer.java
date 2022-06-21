package cashfree.transformers.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.net.URL;

public class NestedUrlDeserializer extends StdDeserializer<URL> {

    public NestedUrlDeserializer() {
        this(null);
    }

    public NestedUrlDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public URL deserialize(JsonParser jp, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        URL url = new URL(node.get("url").asText());
        return url;
    }
}
