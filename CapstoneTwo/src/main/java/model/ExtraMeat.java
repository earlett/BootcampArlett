package model;

public enum ExtraMeat {
    FOUR_INCH(4, 0.50),
    EIGHT_INCH(8, 1.00),
    TWELVE_INCH(12, 1.50);

    private final int breadLength;
    private final double cost;

    ExtraMeat(int breadLength, double cost) {
        this.breadLength = breadLength;
        this.cost = cost;
    }

    public int getBreadLength() {
        return breadLength;
    }

    public double getCost() {
        return cost;
    }

    public static ExtraMeat fromSize(int size) {
        for (ExtraMeat extra : values()) {
            if (extra.breadLength == size) {
                return extra;
            }
        }
        return null;
    }
}