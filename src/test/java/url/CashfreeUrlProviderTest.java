package url;

import cashfree.cashfreeUrl.CashfreeOrdersUrl;
import cashfree.cashfreeUrl.CashfreeUrlConstants;
import cashfree.cashfreeUrl.CashfreeUrlProvider;
import cashfree.config.CashfreeHttpClientConfig;
import cashfree.config.Domain;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CashfreeUrlProviderTest {

    CashfreeUrlProvider cashfreeUrlProvider;

    @BeforeAll
    public void init() {
        cashfreeUrlProvider = new CashfreeUrlProvider();
    }

    private Stream<Arguments> getOrdersUrlTestParamsProvider() {
        CashfreeHttpClientConfig cashfreeHttpClientConfigLocal = CashfreeHttpClientConfig.builder()
                .apiVersion("2022-01-01")
                .domain(Domain.LOCAL)
                .build();
        CashfreeHttpClientConfig cashfreeHttpClientConfigGamma = CashfreeHttpClientConfig.builder()
                .apiVersion("2022-01-01")
                .domain(Domain.GAMMA)
                .build();
        CashfreeOrdersUrl cashfreeOrdersUrlLocalExpected = CashfreeOrdersUrl.builder()
                .baseUrl(String.format("%s:%s", CashfreeUrlConstants.LOCAL_BASE_URL, CashfreeUrlConstants.DEFAULT_LOCAL_PORT))
                .createOrdersPath(CashfreeUrlConstants.CREATE_ORDER_PATH_LOCAL_V1)
                .getOrderPath(CashfreeUrlConstants.GET_ORDER_PATH_LOCAL_V1)
                .payOrderPath(CashfreeUrlConstants.PAY_ORDER_PATH_LOCAL_V1)
                .build();
        CashfreeOrdersUrl cashfreeOrdersUrlGammaExpected = CashfreeOrdersUrl.builder()
                .baseUrl(CashfreeUrlConstants.GAMMA_BASE_URL)
                .createOrdersPath(CashfreeUrlConstants.CREATE_ORDER_PATH_GAMMA_V1)
                .getOrderPath(CashfreeUrlConstants.GET_ORDER_PATH_GAMMA_V1)
                .payOrderPath(CashfreeUrlConstants.PAY_ORDER_PATH_GAMMA_V1)
                .build();

        return Stream.of(
                Arguments.of(cashfreeHttpClientConfigLocal, cashfreeOrdersUrlLocalExpected),
                Arguments.of(cashfreeHttpClientConfigGamma, cashfreeOrdersUrlGammaExpected)
        );
    }

    @ParameterizedTest
    @MethodSource("getOrdersUrlTestParamsProvider")
    void getOrdersUrlTest(CashfreeHttpClientConfig cashfreeHttpClientConfig,
                                 CashfreeOrdersUrl cashfreeOrdersUrlExpected) {
        CashfreeOrdersUrl cashfreeOrdersUrlLocal = cashfreeUrlProvider.getOrdersUrl(cashfreeHttpClientConfig);
        assertThat(cashfreeOrdersUrlLocal).isEqualToComparingFieldByFieldRecursively(cashfreeOrdersUrlExpected);
    }
}
