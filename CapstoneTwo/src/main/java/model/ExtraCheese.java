package model;

public enum ExtraCheese {
    FOUR_INCH(4, 0.30),
    EIGHT_INCH(8, 0.60),
    TWELVE_INCH(12, 0.90);

    private final int breadLength;
    private final double cost;

    ExtraCheese(int breadLength, double cost) {
        this.breadLength = breadLength;
        this.cost = cost;
    }

    public int getBreadLength() {
        return breadLength;
    }

    public double getCost() {
        return cost;
    }

    public static ExtraCheese fromSize(int size) {
        for (ExtraCheese extra : values()) {
            if (extra.breadLength == size) {
                return extra;
            }
        }
        return null;
    }
}