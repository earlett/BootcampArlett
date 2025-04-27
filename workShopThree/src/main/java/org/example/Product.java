package org.example;

public class Product {
    private String sku;
    String productName;
    double price;
    String department;

    public Product(String sKU, String productName, double price, String department) {
        this.sku = sKU;
        this.productName = productName;
        this.price = price;
        this.department = department;
    }

    public String getsKU() {
        return sku;
    }

    public void setsKU(String sKU) {
        this.sku = sKU;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
