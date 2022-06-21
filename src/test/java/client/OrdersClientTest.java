package client;

import cashfree.cashfreeUrl.CashfreeOrdersUrl;
import cashfree.cashfreeUrl.CashfreeUrlConstants;
import cashfree.cashfreeUrl.CashfreeUrlProvider;
import cashfree.client.OrdersClient;
import cashfree.config.CashfreeAuthConfig;
import cashfree.config.ConfigProvider;
import cashfree.exception.CashfreeException;
import cashfree.exception.CashfreeUrlException;
import cashfree.models.request.CreateOrderRequest;
import cashfree.models.request.GetOrderRequest;
import cashfree.models.request.PayOrderRequest;
import cashfree.net.http.CashfreeHttpClient;
import cashfree.net.http.CashfreeHttpRequest;
import cashfree.net.http.CashfreeHttpResponse;
import cashfree.net.http.HttpHeaders;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static cashfree.main.getResourceAsString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrdersClientTest {

    @Mock
    ConfigProvider configProvider;

    @Mock
    CashfreeUrlProvider cashfreeUrlProvider;

    @Mock
    CashfreeHttpClient cashfreeHttpClient;

    @Mock
    CashfreeOrdersUrl cashfreeOrdersUrl;

    @InjectMocks
    OrdersClient ordersClient;

    @Captor
    ArgumentCaptor<CashfreeHttpRequest> httpRequestArgumentCaptor;

    Logger logger = LogManager.getLogger(OrdersClientTest.class);

    @Test
    public void createOrderTest_success() throws IOException, CashfreeException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            CreateOrderRequest createOrderRequest = objectMapper.readValue(
                    getResourceAsString("src/main/resources/test_data/create_order_request1.json"),
                    CreateOrderRequest.class
            );
            String serializedCreateOrderRequest = objectMapper.writeValueAsString(createOrderRequest);
            String createOrderResponseString = getResourceAsString("src/main/resources/test_data/create_order_response1.json");

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.addAuthHeaders(CashfreeAuthConfig.builder().clientId("test").apiKey("test").build());

            CashfreeHttpRequest httpRequestExpected = CashfreeHttpRequest.builder()
                    .contentByteArray(serializedCreateOrderRequest.getBytes(StandardCharsets.UTF_8))
                    .url(new URL(String.format("%s%s", CashfreeUrlConstants.GAMMA_BASE_URL, CashfreeUrlConstants.CREATE_ORDER_PATH_GAMMA_V1)))
                    .method("POST")
                    .httpHeaders(httpHeaders.getHeadersMap())
                    .build();
            when(this.configProvider.getAuthConfig()).thenReturn(CashfreeAuthConfig.builder().clientId("test").apiKey("test").build());
            when(this.cashfreeOrdersUrl.getBaseUrl()).thenReturn(CashfreeUrlConstants.GAMMA_BASE_URL);
            when(this.cashfreeOrdersUrl.getCreateOrdersPath()).thenReturn(CashfreeUrlConstants.CREATE_ORDER_PATH_GAMMA_V1);
            when(this.cashfreeHttpClient.callWithRetry(any())).thenReturn(CashfreeHttpResponse.builder().body(createOrderResponseString).build());
            ordersClient.createOrder(createOrderRequest);
            Mockito.verify(cashfreeHttpClient).callWithRetry(httpRequestArgumentCaptor.capture());
            CashfreeHttpRequest httpRequestCaptured = httpRequestArgumentCaptor.getValue();
            assertThat(httpRequestCaptured).isEqualToComparingFieldByFieldRecursively(httpRequestExpected);

        } catch (CashfreeException ce) {
            logger.error(ce.getCashfreeErrorMessage());
            throw ce;
        } catch (IOException ioe) {
            logger.error(ioe.getMessage());
            throw ioe;
        }
    }

    @Test
    public void getOrderTest_success() throws IOException, CashfreeException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            GetOrderRequest getOrderRequest = objectMapper.readValue(
                    getResourceAsString("src/main/resources/test_data/get_order_request1.json"),
                    GetOrderRequest.class
            );
            String serializedGetOrderRequest = objectMapper.writeValueAsString(getOrderRequest);
            String getOrderResponseString = getResourceAsString("src/main/resources/test_data/create_order_request1.json");

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.addAuthHeaders(CashfreeAuthConfig.builder().clientId("test").apiKey("test").build());

            CashfreeHttpRequest httpRequestExpected = CashfreeHttpRequest.builder()
                    .url(new URL(String.format("%s%s/%s",
                            CashfreeUrlConstants.GAMMA_BASE_URL,
                            CashfreeUrlConstants.GET_ORDER_PATH_GAMMA_V1,
                            getOrderRequest.getOrderID()
                    )))
                    .method("POST")
                    .httpHeaders(httpHeaders.getHeadersMap())
                    .build();

            when(this.configProvider.getAuthConfig()).thenReturn(CashfreeAuthConfig.builder().clientId("test").apiKey("test").build());
            when(this.cashfreeOrdersUrl.getBaseUrl()).thenReturn(CashfreeUrlConstants.GAMMA_BASE_URL);
            when(this.cashfreeOrdersUrl.getGetOrderPath()).thenReturn(CashfreeUrlConstants.GET_ORDER_PATH_GAMMA_V1);
            when(this.cashfreeHttpClient.callWithRetry(any())).thenReturn(CashfreeHttpResponse.builder().body(getOrderResponseString).build());
            ordersClient.getOrder(getOrderRequest);
            Mockito.verify(cashfreeHttpClient).callWithRetry(httpRequestArgumentCaptor.capture());
            CashfreeHttpRequest capturedHttpRequest = httpRequestArgumentCaptor.getValue();
            assertThat(capturedHttpRequest).isEqualToComparingFieldByFieldRecursively(httpRequestExpected);

        } catch (CashfreeException ce) {
            logger.error(ce.getCashfreeErrorMessage());
            throw ce;
        } catch (IOException ioe) {
            logger.error(ioe.getMessage());
            throw ioe;
        }
    }

    @Test
    public void payOrderTest_success() throws IOException, CashfreeException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            PayOrderRequest payOrderRequest = objectMapper.readValue(
                    getResourceAsString("src/main/resources/test_data/pay_order_request1.json"),
                    PayOrderRequest.class
            );
            String payOrderResponseString = getResourceAsString("src/main/resources/test_data/create_order_request1.json");
            String serializedPayOrderRequest = objectMapper.writeValueAsString(payOrderRequest);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.addAuthHeaders(CashfreeAuthConfig.builder().clientId("test").apiKey("test").build());

            CashfreeHttpRequest httpRequestExpected = CashfreeHttpRequest.builder()
                    .contentByteArray(serializedPayOrderRequest.getBytes(StandardCharsets.UTF_8))
                    .url(new URL(String.format("%s%s", CashfreeUrlConstants.GAMMA_BASE_URL, CashfreeUrlConstants.PAY_ORDER_PATH_GAMMA_V1)))
                    .method("POST")
                    .httpHeaders(httpHeaders.getHeadersMap())
                    .build();

            when(this.configProvider.getAuthConfig()).thenReturn(CashfreeAuthConfig.builder().clientId("test").apiKey("test").build());
            when(this.cashfreeOrdersUrl.getBaseUrl()).thenReturn(CashfreeUrlConstants.GAMMA_BASE_URL);
            when(this.cashfreeOrdersUrl.getPayOrderPath()).thenReturn(CashfreeUrlConstants.PAY_ORDER_PATH_GAMMA_V1);
            when(this.cashfreeHttpClient.callWithRetry(any())).thenReturn(CashfreeHttpResponse.builder().body(payOrderResponseString).build());
            ordersClient.payOrder(payOrderRequest);
            Mockito.verify(cashfreeHttpClient).callWithRetry(httpRequestArgumentCaptor.capture());
            CashfreeHttpRequest capturedHttpRequest = httpRequestArgumentCaptor.getValue();
            assertThat(capturedHttpRequest).isEqualToComparingFieldByFieldRecursively(httpRequestExpected);
        } catch (CashfreeException ce) {
            logger.error(ce.getCashfreeErrorMessage());
            throw ce;
        } catch (IOException ioe) {
            logger.error(ioe.getMessage());
            throw ioe;
        }
    }

    @Test
    public void createOrder_malformedUrlException() throws IOException, CashfreeException{
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            GetOrderRequest getOrderRequest = objectMapper.readValue(
                    getResourceAsString("src/main/resources/test_data/get_order_request1.json"),
                    GetOrderRequest.class
            );
            String serializedGetOrderRequest = objectMapper.writeValueAsString(getOrderRequest);
            String getOrderResponseString = getResourceAsString("src/main/resources/test_data/create_order_request1.json");

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.addAuthHeaders(CashfreeAuthConfig.builder().clientId("test").apiKey("test").build());

            CashfreeHttpRequest httpRequestExpected = CashfreeHttpRequest.builder()
                    .url(new URL(String.format("%s%s/%s",
                            CashfreeUrlConstants.GAMMA_BASE_URL,
                            CashfreeUrlConstants.GET_ORDER_PATH_GAMMA_V1,
                            getOrderRequest.getOrderID()
                    )))
                    .method("POST")
                    .httpHeaders(httpHeaders.getHeadersMap())
                    .build();

            when(this.configProvider.getAuthConfig()).thenReturn(CashfreeAuthConfig.builder().clientId("test").apiKey("test").build());
            when(this.cashfreeOrdersUrl.getBaseUrl()).thenReturn("badvalue");
            when(this.cashfreeOrdersUrl.getGetOrderPath()).thenReturn(CashfreeUrlConstants.GET_ORDER_PATH_GAMMA_V1);
            CashfreeUrlException urlException = assertThrows(CashfreeUrlException.class
                    , () -> ordersClient.getOrder(getOrderRequest)
                    , "");
            assertTrue(urlException.getCause() instanceof MalformedURLException);

        }  catch (IOException ioe) {
            logger.error(ioe.getMessage());
            throw ioe;
        }
    }
}
