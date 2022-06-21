package cashfree.config;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CashfreeAuthConfig {
    public String apiKey;
    public String clientId;
}
