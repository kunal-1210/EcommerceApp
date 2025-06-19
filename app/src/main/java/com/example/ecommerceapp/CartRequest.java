package com.example.ecommerceapp;

import java.util.List;

// CartRequest.java
public class CartRequest {
    private int id; // optional, can be skipped or auto-generated
    private int userId;
    private List<CartProduct> products;

    public CartRequest(int userId, List<CartProduct> products) {
        this.userId = userId;
        this.products  = products;
    }
    public int getUserId() {
        return userId;
    }

    public List<CartProduct> getProducts() {
        return products;
    }

    // Getters and setters (if needed)
}
