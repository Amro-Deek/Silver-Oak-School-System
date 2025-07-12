package com.example.finalproject.activities;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import org.json.*;
import com.example.finalproject.R;

import com.example.finalproject.Yahya.StudentDashboardActivity;
import com.example.finalproject.aseel.MainRegistrar;

public class LoginActivity extends AppCompatActivity {
    EditText username, password;
    Button login,contactus,news,aboutus,events;
    CheckBox rememberMeCheckBox;

    SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "LoginPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.editTextText);
        password = findViewById(R.id.editTextTextPassword);
        login = findViewById(R.id.button3);
        rememberMeCheckBox = findViewById(R.id.checkBox);
        contactus = findViewById(R.id.button7);
        news = findViewById(R.id.button6);
        aboutus = findViewById(R.id.button9);
        events = findViewById(R.id.button5);


        ImageView contactImage = findViewById(R.id.contactImage);
        ImageView newsImage = findViewById(R.id.newsImage);
        ImageView aboutImage = findViewById(R.id.aboutImage);
        ImageView eventsImage = findViewById(R.id.eventsImage);

// Contact Us Image click
        contactImage.setOnClickListener(v -> contactus.performClick());

// News Image click
        newsImage.setOnClickListener(v -> news.performClick());

// About Us Image click
        aboutImage.setOnClickListener(v -> aboutus.performClick());

// Events Image click
        eventsImage.setOnClickListener(v -> events.performClick());
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        loadSavedCredentials();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        login.setOnClickListener(v -> {
            String email = username.getText().toString().trim();
            String pass = password.getText().toString().trim();

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (rememberMeCheckBox.isChecked()) {
                saveCredentials(email, pass);
            } else {
                clearCredentials();
            }

            loginUserByEmail(email, pass);
        });

        news.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, com.example.finalproject.deema.NewsLetter.class);
            startActivity(intent);
        });

        contactus.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, com.example.finalproject.deema.CONTACTUS.class);
            startActivity(intent);
        });

        events.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, com.example.finalproject.deema.EventCalandar.class);
            startActivity(intent);
        });

        aboutus.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, com.example.finalproject.deema.AboutUs.class);
            startActivity(intent);
        });

    }

    private void saveCredentials(String email, String pass) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("password", pass);
        editor.putBoolean("remember", true);
        editor.apply();
    }

    private void clearCredentials() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    private void loadSavedCredentials() {
        boolean remember = sharedPreferences.getBoolean("remember", false);
        if (remember) {
            String email = sharedPreferences.getString("email", "");
            String pass = sharedPreferences.getString("password", "");
            username.setText(email);
            password.setText(pass);
            rememberMeCheckBox.setChecked(true);
        }
    }

    private void loginUserByEmail(String email, String password) {
        String url = "http://10.0.2.2/MobileProject/login.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject postData = new JSONObject();
        try {
            postData.put("email", email);
            postData.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, postData,
                response -> {
                    try {
                        String status = response.getString("status");
                        if (status.equals("success")) {
                            int id = response.getInt("id");
                            String name = response.getString("name");
                            String role = response.getString("role");
                            Toast.makeText(LoginActivity.this, "Welcome " + name + " (" + role + ")", Toast.LENGTH_LONG).show();

                            SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putInt("id", id);
                            editor.putString("user_role", role);
                            editor.apply();

// Redirect based on role
                            Intent intent =new Intent(this, TeacherDashboardActivity.class);;
                            switch (role.toLowerCase()) {
                                case "teacher":
                                    intent = new Intent(this, TeacherDashboardActivity.class);
                                    intent.putExtra("name",name);
                                    intent.putExtra("id",id);
                                    break;
                                case "student":
                                    intent = new Intent(this, StudentDashboardActivity.class);
                                    intent.putExtra("name",name);
                                    intent.putExtra("id",id);
                                    break;
                                case "registrar":
                                    intent = new Intent(this, MainRegistrar.class);
                                    intent.putExtra("name",name);
                                    intent.putExtra("id",id);
                                    break;
                                default:

                                    Toast.makeText(this, "Unknown role: " + role, Toast.LENGTH_SHORT).show();
                                    return;
                            }

                            startActivity(intent);
                            finish();
                            // Navigate to next activity if needed
                        } else {
                            String message = response.optString("message", "Login failed");
                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(LoginActivity.this, "Response parsing error", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    String errorMessage;
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        errorMessage = "Code: " + error.networkResponse.statusCode + ", " + new String(error.networkResponse.data);
                    } else {
                        errorMessage = "Error: " + error.toString();
                    }
                    Toast.makeText(LoginActivity.this, "Request error: " + errorMessage, Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                });

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        queue.add(request);
    }
}