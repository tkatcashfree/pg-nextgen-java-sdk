package cashfree.models;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.net.URL;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UpiLinksPayloadResponse implements PayOrderDataPayload {
    URL bhim;
    URL defaultUrl;
    URL gpay;
    URL paytm;
    URL phonepe;
}
