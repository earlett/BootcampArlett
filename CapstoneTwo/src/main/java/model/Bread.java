package model;

public enum Bread {
    WHITE("white"),
    WHEAT("wheat"),
    RYE("rye"),
    WRAP("wrap");

    private final String name;

    Bread(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Bread fromString(String breadType) {
        for (Bread bread : values()) {
            if (bread.name.equalsIgnoreCase(breadType.trim())) {
                return bread;
            }
        }
        return null;
    }

    public static String getAvailableOptions() {
        return "white, wheat, rye, wrap";
    }

    @Override
    public String toString() {
        return name;
    }
}