package com.example.ecommerceapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIInterface {
    @POST("auth/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);
    @GET("products")
    Call<List<Product>> getAllProducts();
    @POST("/carts")
    Call<CartRequest> createCart(@Body CartRequest cartRequest);
    @GET("carts/{id}")
    Call<CartResponse> getCartById(@Path("id") int id);
    @GET("carts/user/{userId}")
    Call<List<CartResponse>> getCartByUserId(@Path("userId") int userId);
    @GET("users")
    Call<List<User>> getAllUsers();


}

