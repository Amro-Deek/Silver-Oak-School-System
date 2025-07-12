package com.example.finalproject.aseel;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalproject.R;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class editteacher extends AppCompatActivity {

    Spinner spinnerTeacherIds;
    EditText idInput, nameInput, emailInput;
    Spinner subjectSpinner, salarySpinner;
    Button saveButton;

    String[] departments = {"Mathematics", "English", "Physics", "Chemistry", "Biology", "Computer Science", "History"};
    String[] salaries = {"3000", "3500", "4000", "4500", "5000", "5500", "6000", "6500", "7000"};
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editteacher);

        spinnerTeacherIds = findViewById(R.id.spinnerTeacherIds);
        idInput = findViewById(R.id.editTeacherId);
        nameInput = findViewById(R.id.editTeacherName);
        emailInput = findViewById(R.id.editTeacherEmail);
        subjectSpinner = findViewById(R.id.editSubjectSpinner);
        salarySpinner = findViewById(R.id.editExperienceSpinner); // using salary instead of experience
        saveButton = findViewById(R.id.btnSaveTeacherChanges);

        queue = Volley.newRequestQueue(this);

        // Set up department spinner
        ArrayAdapter<String> deptAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, departments);
        deptAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjectSpinner.setAdapter(deptAdapter);

        // Set up salary spinner
        ArrayAdapter<String> salaryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, salaries);
        salaryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        salarySpinner.setAdapter(salaryAdapter);

        loadTeacherIds();

        spinnerTeacherIds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                String selectedId = parent.getItemAtPosition(position).toString();
                fetchTeacherData(selectedId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        saveButton.setOnClickListener(v -> updateTeacher());
    }

    private void loadTeacherIds() {
        String url = "http://10.0.2.2:80/MobileProject/getTeacherIds.php";

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        List<String> ids = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            ids.add(jsonArray.getString(i));
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ids);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerTeacherIds.setAdapter(adapter);
                        Toast.makeText(this, "✔ Teacher IDs loaded", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(this, "❌ JSON parse error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(this, "❌ Network Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                });

        queue.add(request);
    }

    private void fetchTeacherData(String teacherId) {
        String url = "http://10.0.2.2:80/MobileProject/getTeacherById.php";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject json = new JSONObject(response);
                        if (json.getString("status").equals("success")) {
                            JSONObject t = json.getJSONObject("teacher");

                            idInput.setText(t.getString("user_id"));
                            nameInput.setText(t.getString("full_name"));
                            emailInput.setText(t.getString("email"));

                            String dept = t.getString("department");
                            for (int i = 0; i < departments.length; i++) {
                                if (departments[i].equalsIgnoreCase(dept)) {
                                    subjectSpinner.setSelection(i);
                                    break;
                                }
                            }

                            String salary = t.getString("salary");
                            for (int i = 0; i < salaries.length; i++) {
                                if (salaries[i].equals(salary)) {
                                    salarySpinner.setSelection(i);
                                    break;
                                }
                            }
                        } else {
                            Toast.makeText(this, "❌ Teacher not found", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(this, "❌ Parse error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(this, "❌ Fetch error: " + error.getMessage(), Toast.LENGTH_SHORT).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("user_id", teacherId);
                return map;
            }
        };

        queue.add(request);
    }

    private void updateTeacher() {
        String url = "http://10.0.2.2:80/MobileProject/editTeacher.php";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    switch (response.trim()) {
                        case "success":
                            Toast.makeText(this, "Teacher updated successfully!", Toast.LENGTH_SHORT).show();
                            break;
                        case "not_found":
                            Toast.makeText(this, "❌ Teacher ID not found", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Toast.makeText(this, "❌ Server error: " + response, Toast.LENGTH_LONG).show();
                            break;
                    }
                },
                error -> Toast.makeText(this, "❌ Update failed: " + error.getMessage(), Toast.LENGTH_SHORT).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("user_id", idInput.getText().toString().trim());
                map.put("full_name", nameInput.getText().toString().trim());
                map.put("email", emailInput.getText().toString().trim());
                map.put("subject", subjectSpinner.getSelectedItem().toString()); // department
                map.put("experience_years", salarySpinner.getSelectedItem().toString()); // salary
                return map;
            }
        };

        queue.add(request);
    }
}
