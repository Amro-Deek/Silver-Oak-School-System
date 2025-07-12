package com.example.finalproject.aseel;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalproject.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddTeacher extends AppCompatActivity {

    EditText editTeacherId, editFullName, editEmail, editPhone, editGender, editDOB, editAddress;
    Spinner subjectSpinner, experienceSpinner;
    Button btnAddTeacher;

    String selectedSubject = "";
    String selectedExperience = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);

        editTeacherId = findViewById(R.id.teacherIdInput);
        editFullName = findViewById(R.id.nameInput);
        editEmail = findViewById(R.id.emailInput);
        editPhone = findViewById(R.id.phoneInput);
        editGender = findViewById(R.id.genderInput);
        editDOB = findViewById(R.id.dobInput);
        editAddress = findViewById(R.id.addressInput);
        subjectSpinner = findViewById(R.id.subjectSpinner);
        experienceSpinner = findViewById(R.id.experienceSpinner);
        btnAddTeacher = findViewById(R.id.addTeacherButton);

        String[] subjects = {"Mathematics", "English", "Physics", "Chemistry", "Biology", "Computer Science", "History", "Geography", "Islamic Studies", "Art"};
        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subjects);
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjectSpinner.setAdapter(subjectAdapter);
        subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSubject = subjects[position];
            }
            public void onNothingSelected(AdapterView<?> parent) {
                selectedSubject = "";
            }
        });

        String[] years = new String[41];
        for (int i = 0; i <= 40; i++) years[i] = String.valueOf(i);

        ArrayAdapter<String> expAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);
        expAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        experienceSpinner.setAdapter(expAdapter);
        experienceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedExperience = years[position];
            }
            public void onNothingSelected(AdapterView<?> parent) {
                selectedExperience = "0";
            }
        });

        btnAddTeacher.setOnClickListener(v -> {
            String id = editTeacherId.getText().toString().trim();
            String name = editFullName.getText().toString().trim();
            String email = editEmail.getText().toString().trim();
            String phone = editPhone.getText().toString().trim();
            String gender = editGender.getText().toString().trim();
            String dob = editDOB.getText().toString().trim();
            String address = editAddress.getText().toString().trim();

            if (id.isEmpty() || name.isEmpty() || email.isEmpty() || phone.isEmpty() ||
                    gender.isEmpty() || dob.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            String url = "http://10.0.2.2:80/MobileProject/addTeacher.php";
            RequestQueue queue = Volley.newRequestQueue(this);

            StringRequest request = new StringRequest(Request.Method.POST, url,
                    response -> {
                        try {
                            if (response.trim().startsWith("{")) {
                                JSONObject json = new JSONObject(response);
                                String status = json.getString("status");

                                if (status.equals("success")) {
                                    Toast.makeText(this, "✅ Teacher added successfully!", Toast.LENGTH_SHORT).show();
                                    clearFields();
                                } else {
                                    String msg = json.optString("message", "Failed to add teacher");
                                    Toast.makeText(this, "❌ " + msg, Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(this, "❌ Non-JSON response: " + response, Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(this, "❌ Invalid JSON: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    },
                    error -> Toast.makeText(this, "❌ Network Error: " + error.getMessage(), Toast.LENGTH_LONG).show()
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", id);
                    params.put("full_name", name);
                    params.put("email", email);
                    params.put("phone", phone);
                    params.put("gender", gender);
                    params.put("date_of_birth", dob);
                    params.put("address", address);
                    params.put("subject", selectedSubject);
                    params.put("experience_years", selectedExperience);
                    return params;
                }
            };

            queue.add(request);
        });
    }

    private void clearFields() {
        editTeacherId.setText("");
        editFullName.setText("");
        editEmail.setText("");
        editPhone.setText("");
        editGender.setText("");
        editDOB.setText("");
        editAddress.setText("");
        subjectSpinner.setSelection(0);
        experienceSpinner.setSelection(0);
    }
}
