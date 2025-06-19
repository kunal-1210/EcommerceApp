package com.example.ecommerceapp;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String title;
    private double price;
    private String description;
    private String category;
    private String image;
    private Rating rating;

    // Add getters
    public Rating getRating() {
        return rating;
    }
    public int getId() { return id; }
    public String getTitle() { return title; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public String getImage() { return image; }
}

