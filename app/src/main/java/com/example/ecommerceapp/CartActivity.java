package com.example.ecommerceapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private int userId;
    private Button checkoutbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // 👇 Get current user ID from AppDataHolder
        userId = AppDataHolder.getUserId();

        checkoutbtn = findViewById(R.id.checkoutButton);

        Toolbar toolbar = findViewById(R.id.productToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchProductsThenCart(); // ✅ fetch both products and user's cart

        checkoutbtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, Order_summary.class);

            // Pass the correct userId
            intent.putExtra("userId", userId);


            intent.putExtra("name", AppDataHolder.getUserName());
            intent.putExtra("email", AppDataHolder.getUserEmail());
            intent.putExtra("phone", AppDataHolder.getUserPhone());
            intent.putExtra("street", AppDataHolder.getUserStreet());
            intent.putExtra("city", AppDataHolder.getUserCity());
            intent.putExtra("zip", AppDataHolder.getUserZip());


            startActivity(intent);
        });
    }

    private void fetchProductsThenCart() {
        APIInterface api = RetrofitClient.getClient().create(APIInterface.class);

        Call<List<Product>> productCall = api.getAllProducts();
        productCall.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> allProducts = response.body();
                    fetchCart(allProducts); // fetch cart after products are ready
                } else {
                    Log.e("Cart", "❌ Failed to fetch products: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("Cart", "❌ Product API call failed: " + t.getMessage());
            }
        });
    }

    private void fetchCart(List<Product> allProducts) {
        APIInterface api = RetrofitClient.getClient().create(APIInterface.class);
        Call<CartResponse> cartCall = api.getCartById(userId); // Use dynamic userId

        cartCall.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                if (response.isSuccessful()) {
                    CartResponse cart = response.body();
                    Log.d("Cart", "🛒 Fetched cart with products: " + cart.getProducts().size());

                    cartAdapter = new CartAdapter(CartActivity.this, cart.getProducts(), allProducts);
                    cartRecyclerView.setAdapter(cartAdapter);

                    AppDataHolder.setCartProducts(cart.getProducts());
                    AppDataHolder.setAllProducts(allProducts);
                } else {
                    Log.e("Cart", "❌ Failed to fetch cart: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                Log.e("Cart", "❌ Cart API call failed: " + t.getMessage());
            }
        });
    }
}
