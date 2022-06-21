package cashfree.config;

public enum Domain {
    LOCAL("local"),
    TEST("test"),
    GAMMA("gamma"),
    SANDBOX("sandbox"),
    PROD("prod");

    public final String label;

    private Domain(String label) {
        this.label = label;
    }
}
