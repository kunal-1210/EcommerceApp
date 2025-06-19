package com.example.ecommerceapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText emailinput, passwordinput;
    private MaterialButton loginbtn;
    private Button skipbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        emailinput = findViewById(R.id.usernameInput);
        passwordinput = findViewById(R.id.passwordInput);
        loginbtn = findViewById(R.id.loginButton);



        loginbtn.setOnClickListener(v -> {
            String username = emailinput.getText().toString().trim();
            String password = passwordinput.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            LoginRequest loginRequest = new LoginRequest(username, password);
            APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
            Call<LoginResponse> call = apiInterface.loginUser(loginRequest);

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        String token = response.body().getToken();

                        Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                        // 🔄 Now fetch the user ID and email using the entered username
                        fetchUserDetails(username);
                    } else {
                        Toast.makeText(MainActivity.this, "Login Failed. Check credentials.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void fetchUserDetails(String username) {
        APIInterface userApi = RetrofitClient.getClient().create(APIInterface.class);
        Call<List<User>> usersCall = userApi.getAllUsers();

        usersCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (User user : response.body()) {
                        if (user.getUsername().equals(username)) {
                            String fullName = user.getName().getFirstname() + " " + user.getName().getLastname();
                            AppDataHolder.setUserName(fullName);
                            AppDataHolder.setUserId(user.getId());
                            AppDataHolder.setUserName(user.getUsername());
                            AppDataHolder.setUserEmail(user.getEmail());
                            AppDataHolder.setUserStreet(user.getAddress().getStreet());
                            AppDataHolder.setUserCity(user.getAddress().getCity());
                            AppDataHolder.setUserZip(user.getAddress().getZipcode());
                            break;
                        }
                    }

                    // ✅ Navigate to home
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to fetch user details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error loading user info", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
