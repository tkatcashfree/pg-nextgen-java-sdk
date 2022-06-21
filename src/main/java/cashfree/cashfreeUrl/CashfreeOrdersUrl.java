package cashfree.cashfreeUrl;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class CashfreeOrdersUrl {

    @NonNull
    String baseUrl;

    @NonNull
    String createOrdersPath;

    @NonNull
    String payOrderPath;

    @NonNull
    String getOrderPath;
}
