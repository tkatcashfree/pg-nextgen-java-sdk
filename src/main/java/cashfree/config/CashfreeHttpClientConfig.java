package cashfree.config;

import cashfree.cashfreeUrl.CashfreeUrlConstants;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class CashfreeHttpClientConfig {

    @Builder.Default
    Domain domain = Domain.GAMMA;

    @Builder.Default
    int localHostPort = CashfreeUrlConstants.DEFAULT_LOCAL_PORT;

    @Builder.Default
    String apiVersion = "2022-01-01";

    @Builder.Default
    int maximumHttpClientRetries = 5;
}
