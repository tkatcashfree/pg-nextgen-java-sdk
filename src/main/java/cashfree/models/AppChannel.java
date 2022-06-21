package cashfree.models;

public enum AppChannel {
    PHONEPE("phonepe"),
    GPAY("gpay"),
    PAYTM("paytm"),
    AMAZON("amazon"),
    AIRTEL("airtel"),
    FREECHARGE("freecharge"),
    MOBIKWIK("mobikwik"),
    JIO("jio"),
    OLA("ola");

    public final String label;

    private AppChannel(String label) {
        this.label = label;
    }
}
