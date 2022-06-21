package cashfree;

import cashfree.client.OrdersClient;
import cashfree.config.*;
import cashfree.models.*;
import cashfree.models.request.CreateOrderRequest;
import cashfree.models.request.GetOrderRequest;
import cashfree.models.request.PayOrderRequest;
import cashfree.models.response.CreateOrderResponse;
import cashfree.models.response.GetOrderResponse;
import cashfree.models.response.PayOrderResponse;
import cashfree.net.http.HttpConstants;
import cashfree.net.http.HttpHeaders;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

public class main {
    public static void main(String[] args) {
        Logger logger = LogManager.getLogger(main.class);

        ObjectMapper objectMapper = new ObjectMapper();

        CreateOrderRequest createOrderRequest = null;
        GetOrderRequest getOrderRequest = null;
        PayOrderRequest payOrderRequest = null;
        try {
             createOrderRequest = objectMapper.readValue(
                    getResourceAsString("src/main/resources/test_data/create_order_request1.json"),
                    CreateOrderRequest.class
            );
             getOrderRequest = objectMapper.readValue(
                     getResourceAsString("src/main/resources/test_data/get_order_request1.json"),
                     GetOrderRequest.class
             );
             payOrderRequest = objectMapper.readValue(
                     getResourceAsString("src/main/resources/test_data/pay_order_request1.json"),
                     PayOrderRequest.class
             );
        } catch (IOException ioException) {
            logger.error("error while reading test files");

        }

        OrdersClient ordersClient = new OrdersClient(new MainConfigProvider());
        try {
//            OrderSplit orderSplit = OrderSplit.builder()
//                    .amount(BigDecimal.valueOf(1.00))
//                    .vendorId("RAJTEST1")
//                    .percentage(null)
//                    .build();
//            createOrderRequest.setOrderSplits(Collections.singletonList(orderSplit));

            OrderInvoice invoice = OrderInvoice.builder()
                    .gst("1")
                    .gstin("27AAFCN5072P1ZV")
                    .invoiceDate(new Date())
                    .invoiceNumber("inv1633510110513")
                    .invoiceName("sample")
                    .cgst("1")
                    .build();
            HttpHeaders userControlledHeaders = new HttpHeaders();
            userControlledHeaders.replaceMapping(HttpConstants.HTTP_HEADER_REQUEST_ID_KEY, "request1");
            createOrderRequest.setUserControlledHttpHeaders(userControlledHeaders);
//            createOrderRequest.setOrderId("sdsdhsdhs3");
            createOrderRequest.setOrderInvoice(invoice);
            createOrderRequest.setOrderNote("");

//            Map<String, String> orderTags = new HashMap<>();
//            orderTags.put("abcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghij", "abcd");
//            createOrderRequest.setOrderTags(orderTags);

            CreateOrderResponse response = ordersClient.createOrder(createOrderRequest);

            getOrderRequest.setOrderID(createOrderRequest.getOrderId());
            userControlledHeaders.replaceMapping(HttpConstants.HTTP_HEADER_REQUEST_ID_KEY, "request2");
            getOrderRequest.setUserControlledHttpHeaders(userControlledHeaders);
            GetOrderResponse response1 = ordersClient.getOrder(getOrderRequest);
//            Card carAlias = Card.builder().cardAlias("79a42191ca6911e99f370ac953478514")
//                .cardCvv("900").build();
            CardEmi cardEmi =
                CardEmi.builder()
                    .cardNumber("4748461111111111")
                    .expiryMonth("09")
                    .expiryYear("30")
                    .cardCvv("121")
                    .cardBankName("ICICI")
                    .emiTenure(3).build();
            payOrderRequest.setPaymentMethod(cardEmi);
            userControlledHeaders.replaceMapping(HttpConstants.HTTP_HEADER_REQUEST_ID_KEY, "request3");
            payOrderRequest.setUserControlledHttpHeaders(userControlledHeaders);
            payOrderRequest.setOrderToken(response1.getOrderToken());

//            Card card = (Card)payOrderRequest.getPaymentMethod();
//            CardEmi cardEmi = CardEmi.builder()
//                    .cardCvv(card.getCardCvv())
//                    .cardNumber(card.getCardNumber())
//                    .cardBankName("ICICI")
//                    .emiTenure(2)
//                    .expiryMonth(card.getExpiryMonth())
//                    .expiryYear(card.getExpiryYear())
//                    .build();
//            UpiQrCode upiQrCode = UpiQrCode.builder()
//                    .build();
//            UpiLinks upiLinks = UpiLinks.builder()
//                            .build();
//            payOrderRequest.setPaymentMethod(upiLinks);
//            payOrderRequest.setOrderToken("2Mc87rf7ygWXi3MYy5is");
//            logger.error(payOrderRequest);
            PayOrderResponse response2 = ordersClient.payOrder(payOrderRequest);

//            logger.info(response.getPaymentMethod());
            logger.info(response.getCfOrderId());
        } catch (Exception e) {
            logger.error("Exception occured", e);
        }
    }

    public static class MainConfigProvider extends DefaultConfigProvider {

        @Override
        public CashfreeHttpClientConfig getClientConfig() {
            return CashfreeHttpClientConfig.builder()
                    .domain(Enum.valueOf(Domain.class, "SANDBOX"))
                    .apiVersion("2022-01-01")
                    .maximumHttpClientRetries(1)
                    .build();
        }
    }

    public static String getResourceAsString(String path) throws IOException {
        return new String(Files.readAllBytes(
                Paths.get(path))
        );
    }
}
