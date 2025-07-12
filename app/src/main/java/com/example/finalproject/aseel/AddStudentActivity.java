package com.example.finalproject.aseel;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalproject.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddStudentActivity extends AppCompatActivity {
    EditText editName, editEmail, editPassword, editBirthYear, editMajor, editLevel;
    Button btnAddStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        editBirthYear = findViewById(R.id.editBirthYear);
        editMajor = findViewById(R.id.editMajor);
        editLevel = findViewById(R.id.editLevel);
        btnAddStudent = findViewById(R.id.btnAddStudent);


        SharedPreferences prefs = getSharedPreferences("AddStudentPrefs", MODE_PRIVATE);
        editName.setText(prefs.getString("name", ""));
        editEmail.setText(prefs.getString("email", ""));
        editPassword.setText(prefs.getString("password", ""));
        editBirthYear.setText(prefs.getString("birthYear", ""));
        editMajor.setText(prefs.getString("major", ""));
        editLevel.setText(prefs.getString("level", ""));

        btnAddStudent.setOnClickListener(v -> {
            String name = editName.getText().toString().trim();
            String email = editEmail.getText().toString().trim();
            String password = editPassword.getText().toString().trim();
            String birthYear = editBirthYear.getText().toString().trim();
            String major = editMajor.getText().toString().trim();
            String level = editLevel.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || birthYear.isEmpty()
                    || major.isEmpty() || level.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            String url = "http://10.0.2.2:80/MobileProject/addStudent.php";

            StringRequest request = new StringRequest(Request.Method.POST, url,
                    response -> {
                        try {
                            JSONObject json = new JSONObject(response);
                            String status = json.getString("status");
                            if (status.equals("success")) {
                                Toast.makeText(this, "Student added successfully!", Toast.LENGTH_SHORT).show();
                                clearFields();
                            } else {
                                String msg = json.optString("message", "Failed to add student");
                                Toast.makeText(this, "❌ " + msg, Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(this, "❌ Invalid response from server", Toast.LENGTH_LONG).show();
                        }
                    },
                    error -> Toast.makeText(this, "❌ Network Error: " + error.getMessage(), Toast.LENGTH_LONG).show()
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("name", name);
                    params.put("email", email);
                    params.put("password", password);
                    params.put("birth_year", birthYear);
                    params.put("major", major);
                    params.put("level", level);
                    return params;
                }
            };

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        getSharedPreferences("AddStudentPrefs", MODE_PRIVATE).edit()
                .putString("name", editName.getText().toString())
                .putString("email", editEmail.getText().toString())
                .putString("password", editPassword.getText().toString())
                .putString("birthYear", editBirthYear.getText().toString())
                .putString("major", editMajor.getText().toString())
                .putString("level", editLevel.getText().toString())
                .apply();
    }

    private void clearFields() {
        editName.setText("");
        editEmail.setText("");
        editPassword.setText("");
        editBirthYear.setText("");
        editMajor.setText("");
        editLevel.setText("");


        getSharedPreferences("AddStudentPrefs", MODE_PRIVATE).edit().clear().apply();
    }
}
