package com.example.ecommerceapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsActivity extends AppCompatActivity {

    ImageView imageView;
    TextView titleView, priceView, descriptionView, ratingView;
    Button addToCartBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        imageView = findViewById(R.id.productImage);
        titleView = findViewById(R.id.productTitle);
        priceView = findViewById(R.id.productPrice);
        descriptionView = findViewById(R.id.productDescription);
        ratingView = findViewById(R.id.productRating);
        addToCartBtn = findViewById(R.id.addToCartBtn);

        // Get product from intent
        Product product = (Product) getIntent().getSerializableExtra("product");

        // Toolbar with back button
        Toolbar toolbar = findViewById(R.id.productToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        if (product != null) {
            // Display product info
            titleView.setText(product.getTitle());
            priceView.setText("₹ " + product.getPrice());
            descriptionView.setText(product.getDescription());
            ratingView.setText("★ " + product.getRating().getRate());
            Glide.with(this).load(product.getImage()).into(imageView);

            // Add to Cart button logic
            addToCartBtn.setOnClickListener(v -> {
                CartManager.addToCart(product);
                postProductToCartAPI(product);
                Toast.makeText(this, "Added to Cart", Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void postProductToCartAPI(Product product) {
        APIInterface api = RetrofitClient.getClient().create(APIInterface.class);

        List<CartProduct> productList = new ArrayList<>();
        productList.add(new CartProduct(product.getId(), 1)); // quantity = 1

        CartRequest cartRequest = new CartRequest(1, productList); // userId = 1 (demo)

        Log.d("CartAPI", "Posting cart request: productId=" + product.getId());

        Call<CartRequest> call = api.createCart(cartRequest);

        call.enqueue(new Callback<CartRequest>() {
            @Override
            public void onResponse(Call<CartRequest> call, Response<CartRequest> response) {
                if (response.isSuccessful()) {
                    Log.d("CartAPI", "✅ Product posted to cart successfully");
                } else {
                    Log.e("CartAPI", "❌ API Error Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<CartRequest> call, Throwable t) {
                Log.e("CartAPI", "Failed: " + t.getMessage());
            }
        });
    }
}
