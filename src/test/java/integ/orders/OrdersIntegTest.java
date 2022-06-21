package integ.orders;

import cashfree.client.OrdersClient;
import cashfree.config.*;
import cashfree.exception.CashfreeException;
import cashfree.exception.InvalidRequestException;
import cashfree.models.request.CreateOrderRequest;
import cashfree.models.response.CreateOrderResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static cashfree.main.getResourceAsString;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class OrdersIntegTest {

    OrdersClient ordersClient;
    ConfigProvider configProvider;
    String userName;
    String currentDate;
    String dynamicOrderId;
    String dynamicOrderToken;
    Logger logger;

    @BeforeEach
    public void init() {
        this.configProvider = new DefaultConfigProvider() {

            @Override
            public CashfreeHttpClientConfig getClientConfig() {
                return CashfreeHttpClientConfig.builder()
                        .apiVersion("2022-01-01")
                        .domain(Domain.SANDBOX)
                        .build();
            }
        };

        this.ordersClient = new OrdersClient(this.configProvider);
        this.userName = System.getProperty("user.name");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime now = LocalDateTime.now();
        this.currentDate = dtf.format(now);
        this.dynamicOrderId = String.format("%s%s", userName, currentDate);
        logger = LogManager.getLogger(OrdersIntegTest.class);
    }

    @Test
    public void createOrder() throws JsonProcessingException, CashfreeException {
        CreateOrderRequest createOrderRequest = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            createOrderRequest = objectMapper.readValue(
                    getResourceAsString("src/main/resources/test_data/create_order_request1.json"),
                    CreateOrderRequest.class
            );
            createOrderRequest.setOrderId(this.dynamicOrderId);
        } catch (IOException ioException) {
            logger.error("error while reading testcase files");
        }
        try {
            CreateOrderResponse createOrderResponse = this.ordersClient.createOrder(createOrderRequest);

            assertThat(createOrderResponse.getOrderToken()).isNotNull();
            this.dynamicOrderToken = createOrderResponse.getOrderToken();

        } catch (InvalidRequestException invalidRequestException) {
            logger.error(String.format("%s: %s", invalidRequestException.getHttpErrorCode(), invalidRequestException.getCashfreeErrorMessage()));
            throw invalidRequestException;
        }
    }
}
