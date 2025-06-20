package com.example.evies_car_dealership.model;

public class Customer {
    private int    id;
    private String fullName;
    private String email;
    private String phone;
    private String address;

    /* full constructor used when mapping from DB */
    public Customer(int id, String fullName, String email, String phone, String address) {
        this.id       = id;
        this.fullName = fullName;
        this.email    = email;
        this.phone    = phone;
        this.address  = address;
    }

    public Customer(String fullName, String email) {
        this(0, fullName, email, null, null);
    }

    public Customer(String fullName, String email, String phone, String address) {
        this(0, fullName, email, phone, address);
    }

    /* getters */
    public int    getId()       { return id; }
    public String getFullName() { return fullName; }
    public String getEmail()    { return email; }
    public String getPhone()    { return phone; }
    public String getAddress()  { return address; }
}
