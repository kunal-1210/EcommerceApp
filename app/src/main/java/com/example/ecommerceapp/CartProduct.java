package com.example.ecommerceapp;

public class CartProduct {
    private int productId;
    private int quantity;

    public CartProduct(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}

