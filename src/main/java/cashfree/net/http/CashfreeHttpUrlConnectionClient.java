package cashfree.net.http;

import cashfree.config.ConfigProvider;
import cashfree.config.DefaultConfigProvider;
import cashfree.exception.CashfreeApiException;
import cashfree.exception.CashfreeException;
import cashfree.exception.InvalidRequestException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class CashfreeHttpUrlConnectionClient implements CashfreeHttpClient {

    public static ArrayList<Integer> RETRYABLE_ERROR_CODES = new ArrayList<>(
            Arrays.asList(429, 500, 503)
    );

    public static final Duration ceilDelay = Duration.ofSeconds(5);
    public static final Duration baseDelay = Duration.ofMillis(500);

    public static final int DEFAULT_CONNECT_TIMEOUT = 30 * 1000;
    public static final int DEFAULT_READ_TIMEOUT = 80 * 1000;

    ConfigProvider configProvider;

    public CashfreeHttpUrlConnectionClient() {
        this.configProvider = new DefaultConfigProvider();
    }

    public CashfreeHttpUrlConnectionClient(ConfigProvider configProvider) {
        this.configProvider = configProvider;
    }

    public CashfreeHttpResponse call(CashfreeHttpRequest request) throws InvalidRequestException, CashfreeApiException {
        try {
            final HttpURLConnection conn = createHttpConnection(request);

            int httpResponseCode = conn.getResponseCode();

            if(httpResponseCode < 300) {
                CashfreeHttpResponse response = CashfreeHttpResponse.builder()
                        .code(conn.getResponseCode())
                        .body(new String(readAllBytes(conn.getInputStream())))
                        .httpHeaders(conn.getHeaderFields())
                        .build();
                return response;
            } else if(httpResponseCode > 399 && httpResponseCode < 500) {
                ObjectMapper objectMapper = new ObjectMapper();
                InvalidRequestException invalidRequestException = objectMapper.readValue(
                        new String(readAllBytes(conn.getErrorStream())),
                        InvalidRequestException.class
                );
                invalidRequestException.setHttpErrorCode(httpResponseCode);
                throw invalidRequestException;
            } else {
                ObjectMapper objectMapper = new ObjectMapper();
                CashfreeApiException cashfreeApiException = objectMapper.readValue(
                        new String(readAllBytes(conn.getErrorStream())),
                        CashfreeApiException.class
                );
                cashfreeApiException.setHttpErrorCode(httpResponseCode);
                throw cashfreeApiException;
            }

        } catch (IOException e) {
            throw new CashfreeApiException(
                    String.format("Error while creating API connection: %s", e.getMessage()),
                    "API connection error",
                    "Connection Error",
                    400,
                    e
            );
        }
    }

    public CashfreeHttpResponse callWithRetry(CashfreeHttpRequest request) throws CashfreeException {
        int retryCount = 0;
        CashfreeException retryException = null;
        while(shouldRetry(request, retryCount, retryException)) {
            try {
                CashfreeHttpResponse cashfreeHttpResponse = this.call(request);
                return cashfreeHttpResponse;
            } catch (CashfreeException e) {
                retryException = e;
            }
            retryCount++;

            try {
                Thread.sleep(this.getExponentialSleepTime(retryCount).toMillis());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        throw retryException;
    }

    private static HttpURLConnection createHttpConnection(CashfreeHttpRequest request)
            throws IOException {
        HttpURLConnection conn = (HttpURLConnection) request.url.openConnection();

        conn.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT);
        conn.setReadTimeout(DEFAULT_READ_TIMEOUT);
        conn.setUseCaches(false);

        for (Map.Entry<String, List<String>> entry : request.getHttpHeaders().entrySet()) {
            conn.setRequestProperty(entry.getKey(), String.join(",", entry.getValue()));
        }
        conn.setRequestMethod(request.getMethod());

        if (request.getContentByteArray() != null) {
            conn.setDoOutput(true);
            OutputStream output = conn.getOutputStream();
            output.write(request.getContentByteArray());
            output.close();
        }

        return conn;
    }

    boolean shouldRetry(CashfreeHttpRequest request, int retryCount, Exception e) {

        // refactor
        int maxRetryCount = configProvider.getClientConfig().getMaximumHttpClientRetries();
        if(retryCount > maxRetryCount) {
            return false;
        }

        if(e == null) {
            return true;
        }

        if ((e != null)
                && (e instanceof ConnectException
                || e instanceof SocketTimeoutException)) {
            return true;
        }

        if(e instanceof CashfreeApiException) {
            CashfreeApiException cashfreeApiException = (CashfreeApiException) e;

            if(RETRYABLE_ERROR_CODES.contains(cashfreeApiException.getHttpErrorCode())) {
                return true;
            }
        }

        return false;
    }

    private Duration getExponentialSleepTime(int numRetries) {

        Duration delay =
                Duration.ofNanos((long) (baseDelay.toNanos() * Math.pow(2, numRetries - 1)));

        // Do not allow the number to exceed MaxNetworkRetriesDelay
        if (delay.compareTo(ceilDelay) > 0) {
            delay = ceilDelay;
        }

        double jitter = ThreadLocalRandom.current().nextDouble(0.75, 1.0);
        delay = Duration.ofNanos((long) (delay.toNanos() * jitter));

        if (delay.compareTo(baseDelay) < 0) {
            delay = baseDelay;
        }

        return delay;
    }

    public static byte[] readAllBytes(InputStream inputStream) throws IOException {
        final int bufLen = 1024;
        byte[] buf = new byte[bufLen];
        int readLen;
        IOException exception = null;

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            while ((readLen = inputStream.read(buf, 0, bufLen)) != -1)
                outputStream.write(buf, 0, readLen);

            return outputStream.toByteArray();
        } catch (IOException e) {
            exception = e;
            throw e;
        } finally {
            if (exception == null) inputStream.close();
            else try {
                inputStream.close();
            } catch (IOException e) {
                exception.addSuppressed(e);
            }
        }
    }
}
