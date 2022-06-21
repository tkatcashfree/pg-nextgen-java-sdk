package cashfree.net.http;

import lombok.*;

import java.net.URL;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class CashfreeHttpRequest {

    String method;
    URL url;
    byte[] contentByteArray;
    String contentType;
    Map<String, List<String> > httpHeaders;
}
