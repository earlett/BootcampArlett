package org.example;

public enum AddOn {
    NITROGEN_TIRES("Nitrogen tires", 140.0),
    WINDOW_TINT("Window tinting", 225.0),
    ALL_SEASON_MATS("All-season floor mats", 95.0),
    SPLASH_GUARDS("Splash guards", 85.0),
    CARGO_TRAY("Cargo tray", 110.0),
    WHEEL_LOCKS("Wheel locks", 60.0);

    public final String label;
    public final double price;

    AddOn(String label, double price) {
        this.label = label;
        this.price = price;
    }

    @Override
    public String toString() {
        return label + " ($" + price + ")";
    }
}
