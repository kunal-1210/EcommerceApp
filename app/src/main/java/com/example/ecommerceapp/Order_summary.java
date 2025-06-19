package com.example.ecommerceapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.List;

public class Order_summary extends AppCompatActivity {

    EditText nameInput, emailInput, phoneInput, streetInput, cityInput, zipInput;
    Button orderbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_summary);
        TextView totalAmountText = findViewById(R.id.totalAmountText); // Bind total view
        double grandTotal = 0.0;  // 👈 initialize

        LinearLayout productListContainer = findViewById(R.id.productListContainer);

        orderbtn = findViewById(R.id.confirmOrderBtn);

        nameInput = findViewById(R.id.fullNameInput);
        emailInput = findViewById(R.id.emailInput);
        phoneInput = findViewById(R.id.phoneInput);
        streetInput = findViewById(R.id.streetInput);
        cityInput = findViewById(R.id.cityInput);
        zipInput = findViewById(R.id.zipInput);

        int userId = getIntent().getIntExtra("userId", -1);

        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        String phone = getIntent().getStringExtra("phone");
        String street = getIntent().getStringExtra("street");
        String city = getIntent().getStringExtra("city");
        String zip = getIntent().getStringExtra("zip");

        nameInput.setText(name);
        emailInput.setText(email);
        phoneInput.setText(phone);
        streetInput.setText(street);
        cityInput.setText(city);
        zipInput.setText(zip);

        List<CartProduct> cartProducts = AppDataHolder.getCartProducts();
        List<Product> allProducts = AppDataHolder.getAllProducts();

        for (CartProduct cartProduct : cartProducts) {
            Product matchedProduct = null;
            for (Product product : allProducts) {

                if (product.getId() == cartProduct.getProductId()) {
                    matchedProduct = product;
                    break;
                }
            }

            if (matchedProduct != null) {
                View productView = LayoutInflater.from(this).inflate(R.layout.cart_item_summary, productListContainer, false);

                ImageView imageView = productView.findViewById(R.id.productImage);
                TextView titleView = productView.findViewById(R.id.productTitle);
                TextView qtyView = productView.findViewById(R.id.productQty);
                TextView totalView = productView.findViewById(R.id.productTotal);

                Glide.with(this).load(matchedProduct.getImage()).into(imageView);
                titleView.setText(matchedProduct.getTitle());
                qtyView.setText("Qty: " + cartProduct.getQuantity());

                double total = cartProduct.getQuantity() * matchedProduct.getPrice();
                totalView.setText("₹" + String.format("%.2f", total));
                grandTotal += total;

                productListContainer.addView(productView);
            }
            orderbtn.setOnClickListener(view -> {
                Toast.makeText(this, "Thank you! Order placed successfully.\nPayment gateway integration is coming soon.", Toast.LENGTH_LONG).show();

            });
        }
        totalAmountText.setText("Total: ₹" + String.format("%.2f", grandTotal));
    }
}
