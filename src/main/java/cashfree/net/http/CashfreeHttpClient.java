package cashfree.net.http;

import cashfree.exception.CashfreeException;

public interface CashfreeHttpClient {
    CashfreeHttpResponse call(CashfreeHttpRequest request) throws CashfreeException;

    CashfreeHttpResponse callWithRetry(CashfreeHttpRequest request) throws CashfreeException;
}
