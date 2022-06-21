package cashfree.net.http;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class CashfreeHttpResponse {
    int code;
    Map<String, List<String> > httpHeaders;
    String body;
}
