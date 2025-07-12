package com.example.finalproject.Yahya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentDashboardActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    private final String url = "http://10.0.2.2:80/MobileProject/fetchStudentName.php";
    private int studentId;
    private TextView topNavText;
    private ListView listViewSubjects;
    private ArrayAdapter<String> subjectAdapter;
    private List<String> subjectList = new ArrayList<>();
    SwitchCompat themeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_dashboard);

        themeSwitch = findViewById(R.id.themeSwitch);
        topNavText = findViewById(R.id.topNavText);
        studentId = getIntent().getIntExtra("id", -1);

        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = getSharedPreferences("settings", MODE_PRIVATE).edit();
            editor.putBoolean("dark_mode", isChecked);
            editor.apply();

            AppCompatDelegate.setDefaultNightMode(
                    isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        });

        requestQueue = Volley.newRequestQueue(this);

        fetchStudentName();

        listViewSubjects = findViewById(R.id.listViewSubjects);
        subjectAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, subjectList);
        listViewSubjects.setAdapter(subjectAdapter);

        fetchSubjects();
        setupButtonClickListeners();
        setupBottomNavigation();
    }

    // ✅ CHANGES MADE HERE
    private void setupButtonClickListeners() {
        findViewById(R.id.btnSubmitAssigment).setOnClickListener(v -> {
            Intent intent = new Intent(StudentDashboardActivity.this, ViewAssigmentsActivity.class);
            intent.putExtra("id", studentId); 
            startActivity(intent);
        });

        findViewById(R.id.buttonGpaCalculator).setOnClickListener(v -> {
            Intent intent = new Intent(StudentDashboardActivity.this, GPACalculatorActivity.class);
            intent.putExtra("id", studentId); 
            startActivity(intent);
        });

        findViewById(R.id.buttonToDoList).setOnClickListener(v -> {
            Intent intent = new Intent(StudentDashboardActivity.this, ToDoListActivity.class);
            intent.putExtra("id", studentId); 
            startActivity(intent);
        });
    }

    
    private void setupBottomNavigation() {
        highlightSelectedTab(1); // current tab: Dashboard

        findViewById(R.id.navDashboard).setOnClickListener(v -> {
            highlightSelectedTab(1);
        });

        findViewById(R.id.navSchedule).setOnClickListener(v -> {
            highlightSelectedTab(2);
            Intent intent = new Intent(this, StudentScheduleActivity.class);
            intent.putExtra("id", studentId); 
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });

        findViewById(R.id.navMarks).setOnClickListener(v -> {
            highlightSelectedTab(3);
            Intent intent = new Intent(this, StudentMarksActivity.class);
            intent.putExtra("id", studentId); 
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });
    }

    private void highlightSelectedTab(int tabId) {
        int selectedColor = Color.parseColor("#d90429");
        int unselectedColor = Color.parseColor("#666666");

        TextView t1 = findViewById(R.id.navDashboard).findViewById(R.id.text_dashboard);
        TextView t2 = findViewById(R.id.navSchedule).findViewById(R.id.text_schedule);
        TextView t3 = findViewById(R.id.navMarks).findViewById(R.id.text_marks);

        t1.setTextColor(unselectedColor);
        t2.setTextColor(unselectedColor);
        t3.setTextColor(unselectedColor);

        if (tabId == 1) t1.setTextColor(selectedColor);
        if (tabId == 2) t2.setTextColor(selectedColor);
        if (tabId == 3) t3.setTextColor(selectedColor);
    }

    private void fetchStudentName() {
        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.has("name")) {
                            String name = jsonObject.getString("name");
                            topNavText.setText("Student: " + name);
                        } else {
                            topNavText.setText("Student: Unknown");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        topNavText.setText("Student: Error parsing name");
                    }
                },
                error -> {
                    topNavText.setText("Student: Error fetching name");
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("student_id", String.valueOf(studentId));
                return data;
            }
        };

        requestQueue.add(request);
    }

    private void fetchSubjects() {
        String urlSubjects = "http://10.0.2.2:80/MobileProject/fetchStudentSubjects.php";

        StringRequest request = new StringRequest(Request.Method.POST, urlSubjects,
                response -> {
                    try {
                        JSONArray array = new JSONArray(response);
                        subjectList.clear();

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            String subjectName = obj.getString("subject_name");
                            subjectList.add(subjectName);
                        }

                        subjectAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Parsing error", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Subject fetch failed", Toast.LENGTH_SHORT).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("student_id", String.valueOf(studentId));
                return data;
            }
        };

        requestQueue.add(request);
    }
}
