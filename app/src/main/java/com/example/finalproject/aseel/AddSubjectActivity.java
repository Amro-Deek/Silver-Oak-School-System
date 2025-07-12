package com.example.finalproject.aseel;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalproject.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddSubjectActivity extends AppCompatActivity {

    EditText editSubjectName;
    Spinner spinnerTeacherId;
    Button btnAddSubject;

    ArrayList<String> teacherLabels = new ArrayList<>();
    Map<String, String> labelToIdMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        editSubjectName = findViewById(R.id.editSubjectName);
        spinnerTeacherId = findViewById(R.id.spinnerTeacherId);
        btnAddSubject = findViewById(R.id.btnAddSubject);

        loadTeachers();

        SharedPreferences prefs = getSharedPreferences("AddSubjectPrefs", MODE_PRIVATE);
        editSubjectName.setText(prefs.getString("subjectName", ""));
        String savedTeacher = prefs.getString("selectedTeacher", "");

        btnAddSubject.setOnClickListener(v -> {
            String subjectName = editSubjectName.getText().toString().trim();
            String selectedLabel = spinnerTeacherId.getSelectedItem().toString();
            String selectedTeacherId = labelToIdMap.get(selectedLabel);

            if (subjectName.isEmpty()) {
                Toast.makeText(this, "Please enter a subject name", Toast.LENGTH_SHORT).show();
                return;
            }

            String url = "http://10.0.2.2:80/MobileProject/addSubject.php";

            StringRequest request = new StringRequest(Request.Method.POST, url,
                    response -> {
                        if (response.contains("success")) {
                            Toast.makeText(this, "✅ Subject added!", Toast.LENGTH_SHORT).show();
                            clearFields();
                        } else {
                            Toast.makeText(this, "❌ Error: " + response, Toast.LENGTH_LONG).show();
                        }
                    },
                    error -> Toast.makeText(this, "❌ Network error", Toast.LENGTH_SHORT).show()
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("name", subjectName);
                    params.put("teacher_id", selectedTeacherId);
                    return params;
                }
            };

            Volley.newRequestQueue(this).add(request);
        });

        spinnerTeacherId.post(() -> {
            if (!savedTeacher.isEmpty() && teacherLabels.contains(savedTeacher)) {
                int index = teacherLabels.indexOf(savedTeacher);
                spinnerTeacherId.setSelection(index);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = getSharedPreferences("AddSubjectPrefs", MODE_PRIVATE).edit();
        editor.putString("subjectName", editSubjectName.getText().toString());

        if (spinnerTeacherId.getSelectedItem() != null) {
            editor.putString("selectedTeacher", spinnerTeacherId.getSelectedItem().toString());
        }

        editor.apply();
    }

    private void clearFields() {
        editSubjectName.setText("");
        if (!teacherLabels.isEmpty()) spinnerTeacherId.setSelection(0);
        getSharedPreferences("AddSubjectPrefs", MODE_PRIVATE).edit().clear().apply();
    }

    private void loadTeachers() {
        String url = "http://10.0.2.2:80/MobileProject/getTeacherIds.php";

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        teacherLabels.clear();
                        labelToIdMap.clear();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            String id = jsonArray.getString(i);
                            String label = "Teacher ID: " + id;

                            teacherLabels.add(label);
                            labelToIdMap.put(label, id);
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, teacherLabels);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerTeacherId.setAdapter(adapter);

                        SharedPreferences prefs = getSharedPreferences("AddSubjectPrefs", MODE_PRIVATE);
                        String savedTeacher = prefs.getString("selectedTeacher", "");
                        if (!savedTeacher.isEmpty() && teacherLabels.contains(savedTeacher)) {
                            spinnerTeacherId.setSelection(teacherLabels.indexOf(savedTeacher));
                        }

                    } catch (Exception e) {
                        Toast.makeText(this, "❌ Failed to parse teacher list", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "❌ Failed to connect", Toast.LENGTH_SHORT).show()
        );

        Volley.newRequestQueue(this).add(request);
    }
}