package config;

import cashfree.config.CashfreeAuthConfig;
import cashfree.config.CashfreeHttpClientConfig;
import cashfree.config.DefaultConfigProvider;
import cashfree.config.Domain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DefaultConfigProviderTest {

    DefaultConfigProvider defaultConfigProvider;

    @BeforeEach
    public void init() {
        defaultConfigProvider = new DefaultConfigProvider();
    }

    @Test
    public void getAuthConfigTest() {
        String apiKey = System.getenv().get("cashfreeApiKey");
        String clientId = System.getenv().get("cashfreeClientId");
        CashfreeAuthConfig cashfreeAuthConfig = this.defaultConfigProvider.getAuthConfig();
        CashfreeAuthConfig cashfreeAuthConfigExpected = CashfreeAuthConfig.builder()
                .apiKey(apiKey)
                .clientId(clientId)
                .build();

        assertThat(cashfreeAuthConfig).isEqualToComparingFieldByFieldRecursively(cashfreeAuthConfigExpected);
    }

    @Test
    public void getClientConfigTest() {
        CashfreeHttpClientConfig httpClientConfigExpected = CashfreeHttpClientConfig.builder()
                .apiVersion("2022-01-01")
                .domain(Domain.GAMMA)
                .build();
        CashfreeHttpClientConfig cashfreeHttpClientConfig = this.defaultConfigProvider.getClientConfig();

        assertThat(cashfreeHttpClientConfig).isEqualToComparingFieldByFieldRecursively(httpClientConfigExpected);
    }
}
