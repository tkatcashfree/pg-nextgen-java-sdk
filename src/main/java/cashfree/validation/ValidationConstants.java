package cashfree.validation;

import java.util.HashMap;
import java.util.Map;

public class ValidationConstants {

  public static final Map<String, Boolean> SUPPORTED_PAYMENT_METHODS = new HashMap<String, Boolean>() {{
    put("`cc", true);
    put("dc", true);
    put("ccc", true);
    put("ppc", true);
    put("nb", true);
    put("emi", true);
    put("upi", true);
    put("paypal", true);
    put("app", true);
  }};

}
