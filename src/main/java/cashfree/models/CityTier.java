package cashfree.models;

public enum CityTier {
    TIER1("TIER1"),
    TIER2("TIER2"),
    TIER3("TIER3"),
    TIER4("TIER4"),
    TIER5("TIER5"),
    TIER6("TIER6");

    public final String label;

    private CityTier(String label) {
        this.label = label;
    }
}
