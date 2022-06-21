package cashfree.client;

import cashfree.cashfreeUrl.CashfreeOrdersUrl;
import cashfree.cashfreeUrl.CashfreeUrlProvider;
import cashfree.config.ConfigProvider;
import cashfree.config.DefaultConfigProvider;
import cashfree.exception.CashfreeException;
import cashfree.exception.CashfreeUrlException;
import cashfree.exception.CashfreeValidationException;
import cashfree.exception.InvalidRequestException;
import cashfree.models.*;
import cashfree.models.request.CreateOrderRequest;
import cashfree.models.request.GetOrderRequest;
import cashfree.models.request.PayOrderRequest;
import cashfree.models.response.CreateOrderResponse;
import cashfree.models.response.GetOrderResponse;
import cashfree.models.response.PayOrderResponse;
import cashfree.net.http.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static cashfree.validation.RequestValidation.validateCreateOrderRequest;
import static cashfree.validation.RequestValidation.validatePayOrderRequest;

public class OrdersClient {

    CashfreeHttpClient cashfreeHttpClient;

    ConfigProvider configProvider;

    CashfreeOrdersUrl cashfreeOrdersUrl;

    CashfreeUrlProvider urlProvider;

    Logger logger;

    public OrdersClient() {
        this.configProvider = new DefaultConfigProvider();
        this.cashfreeHttpClient = new CashfreeHttpUrlConnectionClient(this.configProvider);
        this.urlProvider = new CashfreeUrlProvider();
        this.cashfreeOrdersUrl = this.urlProvider.getOrdersUrl(this.configProvider.getClientConfig());
        logger = LogManager.getLogger(OrdersClient.class);
    }

    public OrdersClient(ConfigProvider configProvider) {
        this.configProvider = configProvider;
        this.cashfreeHttpClient = new CashfreeHttpUrlConnectionClient(configProvider);
        this.urlProvider = new CashfreeUrlProvider();
        this.cashfreeOrdersUrl = this.urlProvider.getOrdersUrl(this.configProvider.getClientConfig());
        logger = LogManager.getLogger(OrdersClient.class);
    }

    public OrdersClient(ConfigProvider configProvider,
                        CashfreeHttpClient cashfreeHttpClient,
                        CashfreeUrlProvider cashfreeUrlProvider,
                        CashfreeOrdersUrl cashfreeOrdersUrl) {
        this.configProvider = configProvider;
        this.cashfreeHttpClient = cashfreeHttpClient;
        this.urlProvider = cashfreeUrlProvider;
        this.cashfreeOrdersUrl = cashfreeOrdersUrl;
        logger = LogManager.getLogger(OrdersClient.class);
    }

    public CreateOrderResponse createOrder(CreateOrderRequest orderRequest) throws
            JsonProcessingException, CashfreeException {

        try {

            validateCreateOrderRequest(orderRequest);

            mergeOrderTagsAndInvoice(orderRequest);

            ObjectMapper mapper = new ObjectMapper();
            String serializedRequest = mapper.writeValueAsString(orderRequest);

            HttpHeaders httpHeaders = processHttpHeaders(orderRequest.getUserControlledHttpHeaders());

            CashfreeHttpRequest httpRequest = CashfreeHttpRequest.builder()
                    .contentByteArray(serializedRequest.getBytes(StandardCharsets.UTF_8))
                    .url(new URL(String.format("%s%s", this.cashfreeOrdersUrl.getBaseUrl(), this.cashfreeOrdersUrl.getCreateOrdersPath())))
                    .method("POST")
                    .httpHeaders(httpHeaders.getHeadersMap())
                    .build();

            CashfreeHttpResponse response = this.cashfreeHttpClient.callWithRetry(httpRequest);
            return mapper.readValue(response.getBody(), CreateOrderResponse.class);

        } catch (MalformedURLException me) {
            logger.error(me.getMessage(), me);
            throw new CashfreeUrlException(me.getMessage(), me);
        } catch (CashfreeValidationException ve) {
            ve.getValidationErrors().forEach(
                    modelValidationError -> {
                        logger.error(String.format("class: %s, attribute: %s, message: %s",
                                modelValidationError.getClassPath(), modelValidationError.getAttribute(), modelValidationError.getMessage()));
                    }
            );
            throw ve;
        } catch (InvalidRequestException ie) {
            logger.error(String.format("Http Code: %s CashfreeErrorType: %s CashfreeErrorCode: %s CashfreeErrorMessage: %s",
                    ie.getHttpErrorCode(),
                    ie.getCashfreeErrorType(),
                    ie.getCashfreeErrorCode(),
                    ie.getCashfreeErrorMessage()));
            throw ie;
        }
    }

    public PayOrderResponse payOrder(PayOrderRequest payOrderRequest)
            throws CashfreeException, JsonProcessingException {
        try {

            validatePayOrderRequest(payOrderRequest);

            ObjectMapper mapper = new ObjectMapper();
            String serializedRequest = mapper.writeValueAsString(payOrderRequest);
            logger.error(serializedRequest);

            HttpHeaders httpHeaders = processHttpHeaders(payOrderRequest.getUserControlledHttpHeaders());

            CashfreeHttpRequest httpRequest = CashfreeHttpRequest.builder()
                    .contentByteArray(serializedRequest.getBytes(StandardCharsets.UTF_8))
                    .url(new URL(String.format("%s%s", this.cashfreeOrdersUrl.getBaseUrl(), this.cashfreeOrdersUrl.getPayOrderPath())))
                    .method("POST")
                    .httpHeaders(httpHeaders.getHeadersMap())
                    .build();

            CashfreeHttpResponse response = this.cashfreeHttpClient.callWithRetry(httpRequest);
            return mapper.readValue(response.getBody(), PayOrderResponse.class);

        } catch (MalformedURLException me) {
            logger.error(me.getMessage(), me);
            throw new CashfreeUrlException(me.getMessage(), me);
        } catch (CashfreeValidationException ve) {
            ve.getValidationErrors().forEach(
                    modelValidationError -> {
                        logger.error(String.format("class: %s, attribute: %s, message: %s",
                                modelValidationError.getClassPath(), modelValidationError.getAttribute(), modelValidationError.getMessage()));
                    }
            );
            throw ve;
        } catch (InvalidRequestException ie) {
            logger.error(String.format("Http Code: %s CashfreeErrorType: %s CashfreeErrorCode: %s CashfreeErrorMessage: %s",
                    ie.getHttpErrorCode(),
                    ie.getCashfreeErrorType(),
                    ie.getCashfreeErrorCode(),
                    ie.getCashfreeErrorMessage()));
            throw ie;
        }
    }

    public GetOrderResponse getOrder(GetOrderRequest getOrderRequest)
            throws CashfreeException, JsonProcessingException {
        try {

            HttpHeaders httpHeaders = processHttpHeaders(getOrderRequest.getUserControlledHttpHeaders());

            CashfreeHttpRequest httpRequest = CashfreeHttpRequest.builder()
                    .url(new URL(String.format("%s%s/%s",
                            this.cashfreeOrdersUrl.getBaseUrl(),
                            this.cashfreeOrdersUrl.getGetOrderPath(),
                            getOrderRequest.getOrderID()
                            )))
                    .method("GET")
                    .httpHeaders(httpHeaders.getHeadersMap())
                    .build();

            CashfreeHttpResponse response = this.cashfreeHttpClient.callWithRetry(httpRequest);

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.getBody(), GetOrderResponse.class);

        } catch (MalformedURLException me) {
            logger.error(me.getMessage(), me);
            throw new CashfreeUrlException(me.getMessage(), me);
        } catch (CashfreeValidationException ve) {
            ve.getValidationErrors().forEach(
                    modelValidationError -> {
                        logger.error(String.format("class: %s, attribute: %s, message: %s",
                                modelValidationError.getClassPath(), modelValidationError.getAttribute(), modelValidationError.getMessage()));
                    }
            );
            throw ve;
        } catch (InvalidRequestException ie) {
            logger.error(String.format("Http Code: %s CashfreeErrorType: %s CashfreeErrorCode: %s CashfreeErrorMessage: %s",
                    ie.getHttpErrorCode(),
                    ie.getCashfreeErrorType(),
                    ie.getCashfreeErrorCode(),
                    ie.getCashfreeErrorMessage()));
            throw ie;
        }
    }

    void mergeOrderTagsAndInvoice(CreateOrderRequest orderRequest) {
        if(orderRequest.getOrderInvoice() != null) {
            OrderInvoice invoice = orderRequest.getOrderInvoice();
            Map<String, String> orderTags;
            if(orderRequest.getOrderTags() != null) {
                orderTags =  orderRequest.getOrderTags();
            } else {
                orderTags =  new HashMap<>();
                orderRequest.setOrderTags(orderTags);
            }

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            orderTags.put("gst", invoice.getGst());
            orderTags.put("gstin", invoice.getGstin());
            orderTags.put("invoice_date", dtf.format(invoice.getInvoiceDate().toInstant().atZone(ZoneId.systemDefault())));
            orderTags.put("invoice_number", invoice.getInvoiceNumber());
            orderTags.put("invoice_name", invoice.getInvoiceName());

            //nullable attributes
            if(invoice.getInvoiceLink() != null) {
                orderTags.put("invoice_link", invoice.getInvoiceLink().toString());
            }
            if(invoice.getCgst() != null) {
                orderTags.put("cgst", invoice.getCgst());
            }
            if(invoice.getSgst() != null) {
                orderTags.put("sgst", invoice.getSgst());
            }
            if(invoice.getIgst() != null) {
                orderTags.put("igst", invoice.getIgst());
            }
            if(invoice.getCess() != null) {
                orderTags.put("cess", invoice.getCess());
            }
            if(invoice.getGstIncentive() != null) {
                orderTags.put("gst_incentive", invoice.getGstIncentive());
            }
            if(invoice.getGstPercentage() != null) {
                orderTags.put("gst_percentage", invoice.getGstPercentage());
            }
            if(invoice.getPincode() != null) {
                orderTags.put("pincode", invoice.getPincode());
            }
            if(invoice.getCityTier() != null) {
                orderTags.put("city_tier", invoice.getCityTier().label);
            }
        }
    }

    HttpHeaders processHttpHeaders(HttpHeaders userControlledHttpHeaders) {
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.addAuthHeaders(configProvider.getAuthConfig());
        httpHeaders.addApiVersionHeader(configProvider.getClientConfig());

        if(userControlledHttpHeaders != null && userControlledHttpHeaders.getHeadersMap() != null) {
            userControlledHttpHeaders.getHeadersMap().forEach(
                (key, value) -> {
                    httpHeaders.replaceMapping(key, value);
                }
            );
        }

        return httpHeaders;
    }
}
