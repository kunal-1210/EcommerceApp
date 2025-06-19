package com.example.ecommerceapp;

import java.util.List;

public class AppDataHolder {

    private static List<CartProduct> cartProducts;
    private static List<Product> allProducts;

    private static int userId = -1; // default: no user
    private static String userName;
    private static String userEmail;
    private static String userPhone;
    private static String userStreet;
    private static String userCity;
    private static String userZip;

    // Setters
    public static void setUserId(int id) {
        userId = id;
    }

    public static void setUserName(String name) {
        userName = name;
    }

    public static void setUserEmail(String email) {
        userEmail = email;
    }

    public static void setUserPhone(String phone) {
        userPhone = phone;
    }

    public static void setUserStreet(String street) {
        userStreet = street;
    }

    public static void setUserCity(String city) {
        userCity = city;
    }

    public static void setUserZip(String zip) {
        userZip = zip;
    }

    public static void setCartProducts(List<CartProduct> products) {
        cartProducts = products;
    }

    public static void setAllProducts(List<Product> products) {
        allProducts = products;
    }

    // Getters
    public static int getUserId() {
        return userId;
    }

    public static String getUserName() {
        return userName;
    }

    public static String getUserEmail() {
        return userEmail;
    }

    public static String getUserPhone() {
        return userPhone;
    }

    public static String getUserStreet() {
        return userStreet;
    }

    public static String getUserCity() {
        return userCity;
    }

    public static String getUserZip() {
        return userZip;
    }

    public static List<CartProduct> getCartProducts() {
        return cartProducts;
    }

    public static List<Product> getAllProducts() {
        return allProducts;
    }

    // Clear all user + app data
    public static void clear() {
        cartProducts = null;
        allProducts = null;
        userId = -1;
        userName = null;
        userEmail = null;
        userPhone = null;
        userStreet = null;
        userCity = null;
        userZip = null;
    }
}
