package com.example.ecommerceapp;

import java.util.List;

public class CartResponse {
    private int id;
    private int userId;
    private List<CartProduct> products;

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public List<CartProduct> getProducts() { return products; }
}

