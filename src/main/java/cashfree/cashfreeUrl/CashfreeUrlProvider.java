package cashfree.cashfreeUrl;

import cashfree.config.CashfreeHttpClientConfig;
import cashfree.config.Domain;

public class CashfreeUrlProvider {

    public CashfreeOrdersUrl getOrdersUrl(CashfreeHttpClientConfig clientConfig) {
        Domain domain = clientConfig.getDomain();
        return getOrdersUrlv1(domain, clientConfig);
    }

    public CashfreeOrdersUrl getOrdersUrlv1(Domain domain, CashfreeHttpClientConfig clientConfig) {
        switch (domain) {
            case LOCAL:
                int port = clientConfig.getLocalHostPort();
                return CashfreeOrdersUrl.builder()
                        .createOrdersPath(CashfreeUrlConstants.CREATE_ORDER_PATH_LOCAL_V1)
                        .payOrderPath(CashfreeUrlConstants.PAY_ORDER_PATH_LOCAL_V1)
                        .getOrderPath(CashfreeUrlConstants.GET_ORDER_PATH_LOCAL_V1)
                        .baseUrl(String.format("%s:%s", CashfreeUrlConstants.LOCAL_BASE_URL, port))
                        .build();
            case TEST:
            case SANDBOX:
                return CashfreeOrdersUrl.builder()
                        .createOrdersPath(CashfreeUrlConstants.CREATE_ORDER_PATH_SANDBOX_V1)
                        .payOrderPath(CashfreeUrlConstants.PAY_ORDER_PATH_SANDBOX_V1)
                        .getOrderPath(CashfreeUrlConstants.GET_ORDER_PATH_SANDBOX_V1)
                        .baseUrl(CashfreeUrlConstants.SANDBOX_BASE_URL)
                        .build();
            case PROD:
                return CashfreeOrdersUrl.builder()
                    .createOrdersPath(CashfreeUrlConstants.CREATE_ORDER_PATH_PROD_V1)
                    .payOrderPath(CashfreeUrlConstants.PAY_ORDER_PATH_PROD_V1)
                    .getOrderPath(CashfreeUrlConstants.GET_ORDER_PATH_PROD_V1)
                    .baseUrl(CashfreeUrlConstants.PROD_BASE_URL)
                    .build();
            case GAMMA:
            default:
                return CashfreeOrdersUrl.builder()
                        .createOrdersPath(CashfreeUrlConstants.CREATE_ORDER_PATH_GAMMA_V1)
                        .payOrderPath(CashfreeUrlConstants.PAY_ORDER_PATH_GAMMA_V1)
                        .getOrderPath(CashfreeUrlConstants.GET_ORDER_PATH_GAMMA_V1)
                        .baseUrl(CashfreeUrlConstants.GAMMA_BASE_URL)
                        .build();
        }
    }
}
