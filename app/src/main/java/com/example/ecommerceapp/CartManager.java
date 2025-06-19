package com.example.ecommerceapp;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static final List<Product> cartItems = new ArrayList<>();

    public static void addToCart(Product product) {
        cartItems.add(product);
    }

    public static void removeFromCart(Product product) {
        cartItems.remove(product);
    }

    public static List<Product> getCartItems() {
        return new ArrayList<>(cartItems); // return copy to avoid direct modifications
    }

    public static void clearCart() {
        cartItems.clear();
    }
}

