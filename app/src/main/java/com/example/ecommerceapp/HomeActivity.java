package com.example.ecommerceapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList;  // Store full list
    private SearchView  searchInput;
    private CheckBox price50_100, price100_200, price200_400;
    private CheckBox rating1_2, rating2_3, rating3_4, rating4_5;
    private TextView gotocartbtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        gotocartbtn = findViewById(R.id.goToCart);
         searchInput= findViewById(R.id.searchInput);
        recyclerView = findViewById(R.id.productRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        price50_100 = findViewById(R.id.price50_100);
        price100_200 = findViewById(R.id.price100_200);
        price200_400 = findViewById(R.id.price200_400);

        rating1_2 = findViewById(R.id.rating1_2);
        rating2_3 = findViewById(R.id.rating2_3);
        rating3_4 = findViewById(R.id.rating3_4);
        rating4_5 = findViewById(R.id.rating4_5);


        fetchProducts();

        gotocartbtn.setOnClickListener(v ->{
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);
        });

        searchInput.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Not needed usually for instant filtering
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapter != null) {
                    adapter.filter(newText);
                }
                return true;
            }
        });
    }
    private void applyFilters() {
        List<Product> filtered = new ArrayList<>();

        for (Product product : productList) {
            double price = product.getPrice();
            double rating = product.getRating().getRate();

            boolean priceOk = (
                (price50_100.isChecked() && price >= 50 && price <= 100) ||
                    (price100_200.isChecked() && price > 100 && price <= 200) ||
                    (price200_400.isChecked() && price > 200 && price <= 400) ||
                    (!price50_100.isChecked() && !price100_200.isChecked() && !price200_400.isChecked()) // No price filter = allow all
            );

            boolean ratingOk = (
                (rating1_2.isChecked() && rating >= 1 && rating <= 2) ||
                    (rating2_3.isChecked() && rating > 2 && rating <= 3) ||
                    (rating3_4.isChecked() && rating > 3 && rating <= 4) ||
                    (rating4_5.isChecked() && rating > 4 && rating <= 5) ||
                    (!rating1_2.isChecked() && !rating2_3.isChecked() && !rating3_4.isChecked() && !rating4_5.isChecked())
            );

            if (priceOk && ratingOk) {
                filtered.add(product);
            }
        }

        adapter.updateData(filtered);
    }

    private void fetchProducts() {
        APIInterface api = RetrofitClient.getClient().create(APIInterface.class);
        Call<List<Product>> call = api.getAllProducts();  // Make sure your APIInterface has getProducts()

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    productList = response.body();
                    adapter = new ProductAdapter(HomeActivity.this, response.body());
                    recyclerView.setAdapter(adapter);
                    setupFilterListeners();
                } else {
                    Log.e("HomeActivity", "Response not successful");
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("HomeActivity", "API call failed: " + t.getMessage());
            }
        });
    }
    private void setupFilterListeners() {
        CompoundButton.OnCheckedChangeListener listener = (buttonView, isChecked) -> applyFilters();

        price50_100.setOnCheckedChangeListener(listener);
        price100_200.setOnCheckedChangeListener(listener);
        price200_400.setOnCheckedChangeListener(listener);

        rating1_2.setOnCheckedChangeListener(listener);
        rating2_3.setOnCheckedChangeListener(listener);
        rating3_4.setOnCheckedChangeListener(listener);
        rating4_5.setOnCheckedChangeListener(listener);
    }


}
