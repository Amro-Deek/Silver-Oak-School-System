package com.example.finalproject.deema;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.android.volley.*;
import org.json.*;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


public class Login extends AppCompatActivity {
    EditText username, password;
    Button login,contactus,news,aboutus,events;
    CheckBox rememberMeCheckBox;

    SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "LoginPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
     /*   setContentView(R.layout.activity_login);

        username = findViewById(R.id.editTextText);
        password = findViewById(R.id.editTextTextPassword);
        login = findViewById(R.id.button3);
        rememberMeCheckBox = findViewById(R.id.checkBox);
        contactus = findViewById(R.id.button7);
        news = findViewById(R.id.button6);
        aboutus = findViewById(R.id.button9);
        events = findViewById(R.id.button5);


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
                Toast.makeText(Login.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(Login.this, NewsLetter.class);
            startActivity(intent);
        });
        contactus.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, CONTACTUS.class);
            startActivity(intent);
        });
        events.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, EventCalandar.class);
            startActivity(intent);
        });
        aboutus.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, AboutUs.class);
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
                            Toast.makeText(Login.this, "Welcome " + name + ", your ID is: " + id, Toast.LENGTH_LONG).show();
                            // Navigate to next activity if needed
                        } else {
                            String message = response.optString("message", "Login failed");
                            Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(Login.this, "Response parsing error", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    String errorMessage;
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        errorMessage = "Code: " + error.networkResponse.statusCode + ", " + new String(error.networkResponse.data);
                    } else {
                        errorMessage = "Error: " + error.toString();
                    }
                    Toast.makeText(Login.this, "Request error: " + errorMessage, Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                });

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        queue.add(request);
    }*/
    }
}
