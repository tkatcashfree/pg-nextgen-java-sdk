package cashfree.client;

import cashfree.cashfreeUrl.CashfreeUrlProvider;
import cashfree.config.ConfigProvider;
import cashfree.config.DefaultConfigProvider;
import cashfree.models.OrderNotificationDetails;
import cashfree.models.OrderNotificationPostData;
import cashfree.net.http.CashfreeHttpUrlConnectionClient;
import org.apache.logging.log4j.LogManager;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

public class OrderWebhookNotificationClient {

    ConfigProvider configProvider;

    public OrderWebhookNotificationClient() {
        this.configProvider = new DefaultConfigProvider();
    }

    public OrderWebhookNotificationClient(ConfigProvider configProvider) {
        this.configProvider = configProvider;
    }

    boolean verifyNotification(OrderNotificationPostData orderNotificationPostData) throws
            NoSuchAlgorithmException, UnsupportedEncodingException {
        String signature = orderNotificationPostData.getSignature();
        OrderNotificationDetails orderNotificationDetails = orderNotificationPostData.getOrderNotificationDetails();
        String dataToHash = String.format("%s%s%s%s%s%s%s",
                orderNotificationDetails.getOrderId(),
                orderNotificationDetails.getOrderAmount(),
                orderNotificationDetails.getReferenceId(),
                orderNotificationDetails.getOrderStatus(),
                orderNotificationDetails.getPaymentMode(),
                orderNotificationDetails.getMessage(),
                orderNotificationDetails.getTransactionTime());
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        String key = this.configProvider.getAuthConfig().apiKey;
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        String calculatedSignature = Hex.encodeHexString(sha256_HMAC.doFinal(dataToHash.getBytes("UTF-8")));
        return calculatedSignature.equals(signature);
    }
}
