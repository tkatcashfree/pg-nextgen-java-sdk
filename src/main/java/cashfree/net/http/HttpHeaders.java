package cashfree.net.http;

import static cashfree.net.http.HttpConstants.HTTP_HEADER_ACCEPT_KEY;
import static cashfree.net.http.HttpConstants.HTTP_HEADER_API_VERSION_KEY;
import static cashfree.net.http.HttpConstants.HTTP_HEADER_CLIENT_ID_KEY;
import static cashfree.net.http.HttpConstants.HTTP_HEADER_CONTENT_TYPE_APP_JSON;
import static cashfree.net.http.HttpConstants.HTTP_HEADER_CONTENT_TYPE_KEY;
import static cashfree.net.http.HttpConstants.HTTP_HEADER_SECRET_KEY;

import cashfree.config.CashfreeAuthConfig;
import cashfree.config.CashfreeHttpClientConfig;
import lombok.Getter;

import java.util.*;
@Getter
public class HttpHeaders {

    Map<String, List<String>> headersMap;

    public HttpHeaders() {
        this.headersMap = new HashMap<>();
        this.addCommonHeaderMapping();
    }

    public void addMapping(String key, String value) {
        String normalizedKey = convertKeyToLowerCase(key);
        if(this.headersMap.containsKey(normalizedKey)) {
            this.headersMap.get(normalizedKey).add(value);
        } else {
            List<String> newList = new ArrayList<>();
            newList.add(value);
            this.headersMap.put(normalizedKey, newList);
        }
    }

    public void replaceMapping(String key, String value) {
        String normalizedKey = convertKeyToLowerCase(key);
        List<String> newList = new ArrayList<>();
        newList.add(value);
        this.headersMap.put(normalizedKey, newList);
    }

    public void replaceMapping(String key, List<String> valueList) {
        String normalizedKey = convertKeyToLowerCase(key);
        this.headersMap.put(normalizedKey, valueList);
    }

    private void addCommonHeaderMapping() {
        addMapping(HTTP_HEADER_ACCEPT_KEY, HTTP_HEADER_CONTENT_TYPE_APP_JSON);
        addMapping(HTTP_HEADER_CONTENT_TYPE_KEY, HTTP_HEADER_CONTENT_TYPE_APP_JSON);
    }

    public void addAuthHeaders(CashfreeAuthConfig cashfreeAuthConfig) {
        addMapping(HTTP_HEADER_SECRET_KEY, cashfreeAuthConfig.getApiKey());
        addMapping(HTTP_HEADER_CLIENT_ID_KEY, cashfreeAuthConfig.getClientId());
    }

    public void addApiVersionHeader(CashfreeHttpClientConfig clientConfig) {
        addMapping(HTTP_HEADER_API_VERSION_KEY, clientConfig.getApiVersion());
    }

    private static String convertKeyToLowerCase(String key) {
        if (key == null) {
            return null;
        } else {
            return key.toLowerCase();
        }
    }
}
