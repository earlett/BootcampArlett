package org.example.models;

public class Vehicle {
    private String vin;
    private int year;
    private String make;
    private String model;
    private String color;
    private VehicleType type;
    private int odometer;
    private double price;

    public Vehicle(String vin, int year, String make, String model,
                   String color, VehicleType type, int odometer, double price) {
        this.vin = vin;
        this.year = year;
        this.make = make;
        this.model = model;
        this.color = color;
        this.type = type;
        this.odometer = odometer;
        this.price = price;
    }

    public Vehicle(String csvRow) {
        this(csvRow.split("\\|"));
    }

    public Vehicle(String[] fields) {
        this(fields[0],
             Integer.parseInt(fields[1]),
             fields[2],
             fields[3],
             fields[4],
             VehicleType.valueOf(fields[5]),
             Integer.parseInt(fields[6]),
             Double.parseDouble(fields[7]));
    }

    public String getVin() { return vin; }
    public int getYear() { return year; }
    public String getMake() { return make; }
    public String getModel() { return model; }
    public String getColor() { return color; }
    public VehicleType getType() { return type; }
    public int getOdometer() { return odometer; }
    public double getPrice() { return price; }

    public String toCsvString() {
        return vin + "|" + year + "|" + make + "|" + model + "|" + color
             + "|" + type.name() + "|" + odometer + "|" + String.format("%.2f", price);
    }

    @Override
    public String toString() {
        return toCsvString();
    }
}
