package com.example.ecommerceapp;

public class User {
    private int id;
    private String email;
    private String username;
    private String password;
    private Name name; // nested object for firstname and lastname
    private String phone;
    private Address address;

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Name getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
    public Address getAddress() {
        return address;
    }


}
