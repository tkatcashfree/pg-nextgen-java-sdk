package cashfree.cashfreeUrl;

public class CashfreeUrlConstants {
    public static final String CREATE_ORDER_PATH_LOCAL_V1 = "/api/v1/orders";
    public static final String CREATE_ORDER_PATH_GAMMA_V1 = "/api/v1/orders";
    public static final String CREATE_ORDER_PATH_SANDBOX_V1 = "/pg/orders";
    public static final String CREATE_ORDER_PATH_PROD_V1 = "/pg/orders";

    public static final String PAY_ORDER_PATH_LOCAL_V1 = "/api/v1/orders/pay";
    public static final String PAY_ORDER_PATH_GAMMA_V1 = "/api/v1/orders/pay";
    public static final String PAY_ORDER_PATH_SANDBOX_V1 = "/pg/orders/pay";
    public static final String PAY_ORDER_PATH_PROD_V1 = "/pg/orders/pay";

    public static final String GET_ORDER_PATH_LOCAL_V1 = "/api/v1/orders";
    public static final String GET_ORDER_PATH_GAMMA_V1 = "/api/v1/orders";
    public static final String GET_ORDER_PATH_SANDBOX_V1 = "/pg/orders";
    public static final String GET_ORDER_PATH_PROD_V1 = "/pg/orders";

    public static final String LOCAL_BASE_URL = "http://localhost";
    public static final String GAMMA_BASE_URL = "http://gamma.cashfree.com/pgnextgenapi";
    public static final String SANDBOX_BASE_URL = "http://sandbox.cashfree.com";
    public static final String PROD_BASE_URL = "http://api.cashfree.com";

    public static final int DEFAULT_LOCAL_PORT = 8080;

}
