package model;

public enum Drink {
    SMALL_DRINK('S', 2.00),
    MEDIUM_DRINK('M', 2.50),
    LARGE_DRINK('L', 3.00);

    private final char size;
    private final double cost;

    Drink(char size, double cost) {
        this.size = size;
        this.cost = cost;
    }

    public char getSize() {
        return size;
    }

    public double getCost() {
        return cost;
    }

    public static Drink fromChar(char c) {
        for (Drink d : values()) {
            if (d.size == Character.toUpperCase(c)) {
                return d;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String sizeName = switch (size) {
            case 'S' -> "Small";
            case 'M' -> "Medium";
            case 'L' -> "Large";
            default -> "Unknown";
        };
        return sizeName + " Drink ($" + String.format("%.2f", cost) + ")";
    }
}