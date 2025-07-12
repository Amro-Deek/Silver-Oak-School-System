package com.example.finalproject.aseel;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class editstudent extends AppCompatActivity {

    Spinner spinnerStudentIds;
    EditText editStudentId, editName, editEmail, editBirthYear, editMajor, editLevel;
    Button btnSaveStudentChanges;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editstudent);

        spinnerStudentIds = findViewById(R.id.spinnerStudentIds);
        editStudentId = findViewById(R.id.editStudentId);  // ← was missing
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editBirthYear = findViewById(R.id.editBirthYear);  // ← was missing
        editMajor = findViewById(R.id.editMajor);
        editLevel = findViewById(R.id.editLevel);
        btnSaveStudentChanges = findViewById(R.id.btnSaveStudentChanges);

        queue = Volley.newRequestQueue(this);

        loadStudentIds();

        spinnerStudentIds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedId = parent.getItemAtPosition(position).toString();
                fetchStudentDetails(selectedId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        btnSaveStudentChanges.setOnClickListener(v -> updateStudent());
    }

    void loadStudentIds() {
        String url = "http://10.0.2.2:80/MobileProject/getStudentIds.php";
        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        Log.d("STUDENT_IDS", response);
                        JSONArray jsonArray = new JSONArray(response.trim());
                        List<String> ids = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            ids.add(jsonArray.getString(i));
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ids);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerStudentIds.setAdapter(adapter);
                    } catch (Exception e) {
                        Toast.makeText(this, "❌ Error loading IDs: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(this, "❌ Network error: " + error.getMessage(), Toast.LENGTH_SHORT).show()
        );
        queue.add(request);
    }

    void fetchStudentDetails(String studentId) {
        String url = "http://10.0.2.2:80/MobileProject/getStudentById.php";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        Log.d("FETCH_STUDENT", response);
                        JSONObject json = new JSONObject(response);
                        if (json.getString("status").equals("success")) {
                            JSONObject student = json.getJSONObject("student");
                            editStudentId.setText(student.getString("id"));
                            editName.setText(student.getString("name"));
                            editEmail.setText(student.getString("email"));
                            editBirthYear.setText(student.getString("birth_year"));
                            editMajor.setText(student.getString("major"));
                            editLevel.setText(student.getString("level"));
                        } else {
                            Toast.makeText(this, "Student not found", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(this, "❌ Error parsing student info: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(this, "❌ Network error: " + error.getMessage(), Toast.LENGTH_SHORT).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("student_id", studentId);
                return params;
            }
        };

        queue.add(request);
    }

    void updateStudent() {
        String url = "http://10.0.2.2:80/MobileProject/editStudent.php";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> Toast.makeText(this, "Updated successfully: " , Toast.LENGTH_LONG).show(),
                error -> Toast.makeText(this, "error: " + error.getMessage(), Toast.LENGTH_SHORT).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("student_id", editStudentId.getText().toString().trim());
                params.put("full_name", editName.getText().toString().trim());
                params.put("email", editEmail.getText().toString().trim());
                params.put("birth_year", editBirthYear.getText().toString().trim());
                params.put("major", editMajor.getText().toString().trim());
                params.put("level", editLevel.getText().toString().trim());
                return params;
            }
        };
        queue.add(request);
    }
}
