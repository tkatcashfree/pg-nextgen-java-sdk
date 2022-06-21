package cashfree.config;

public interface ConfigProvider {
    CashfreeAuthConfig getAuthConfig();
    CashfreeHttpClientConfig getClientConfig();
}
