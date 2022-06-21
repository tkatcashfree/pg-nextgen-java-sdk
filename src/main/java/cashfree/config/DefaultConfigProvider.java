package cashfree.config;

public class DefaultConfigProvider implements ConfigProvider {

    public CashfreeAuthConfig getAuthConfig() {
        String apiKey = System.getenv().get("cashfreeApiKey");
        String clientId = System.getenv().get("cashfreeClientId");
        return CashfreeAuthConfig.builder()
                .apiKey(apiKey)
                .clientId(clientId)
                .build();
    }

    public CashfreeHttpClientConfig getClientConfig() {
        return CashfreeHttpClientConfig.builder()
                .build();
    }
}
