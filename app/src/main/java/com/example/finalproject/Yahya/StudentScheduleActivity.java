package com.example.finalproject.Yahya;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StudentScheduleActivity extends AppCompatActivity {

    private List<ScheduleItem> allItems = new ArrayList<>();
    private List<ScheduleItem> items = new ArrayList<>();
    private TextView emptyMessage;
    private Button buttonSun, buttonMon, buttonTue, buttonWed, buttonThu;
    private Button selectedButton = null;

    private RecyclerView recycler;
    private ScheduleAdapter adapter;

    private int studentId = -1; 
    private static final String BASE_URL = "http://10.0.2.2:80/MobileProject/getStudentSchedule.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_schedule);

        
        studentId = getIntent().getIntExtra("id", -1);
        if (studentId == -1) {
            Toast.makeText(this, "Missing student ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        recycler = findViewById(R.id.recyclerViewSchedule);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ScheduleAdapter(this, items);
        recycler.setAdapter(adapter);

        emptyMessage = findViewById(R.id.emptyMessage);

        buttonSun = findViewById(R.id.buttonSun);
        buttonMon = findViewById(R.id.buttonMon);
        buttonTue = findViewById(R.id.buttonTue);
        buttonWed = findViewById(R.id.buttonWed);
        buttonThu = findViewById(R.id.buttonThu);

        View.OnClickListener dayClickListener = v -> {
            Button clicked = (Button) v;
            String day = clicked.getText().toString();

            resetButtonsTextColor();
            clicked.setTextColor(getResources().getColor(android.R.color.white));
            selectedButton = clicked;

            filterByDay(day);
        };

        buttonSun.setOnClickListener(dayClickListener);
        buttonMon.setOnClickListener(dayClickListener);
        buttonTue.setOnClickListener(dayClickListener);
        buttonWed.setOnClickListener(dayClickListener);
        buttonThu.setOnClickListener(dayClickListener);

        selectedButton = buttonTue;
        selectedButton.setTextColor(getResources().getColor(android.R.color.white));

        fetchSchedule();
        setupBottomNavigation();
    }

    private void fetchSchedule() {
        String url = BASE_URL + "?student_id=" + studentId; 

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray array = new JSONArray(response);
                        allItems.clear();

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            String day = obj.getString("day_of_week");
                            String subject = obj.getString("subject_name");
                            String teacher = obj.getString("teacher_name");
                            String startTime = obj.getString("start_time");
                            String endTime = obj.getString("end_time");
                            String location = obj.getString("location");

                            ScheduleItem item = new ScheduleItem(subject, day, teacher, startTime, endTime, location);
                            allItems.add(item);
                        }

                        filterByDay("Tue");
                        Toast.makeText(StudentScheduleActivity.this, "Schedule loaded", Toast.LENGTH_SHORT).show();
                        Log.d("SUCCESS", "Schedule loaded successfully");

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(StudentScheduleActivity.this, "Failed to parse schedule", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(StudentScheduleActivity.this, "Connection error", Toast.LENGTH_SHORT).show();
                    if (error.networkResponse != null) {
                        Log.e("VolleyError", "Status code: " + error.networkResponse.statusCode);
                    }
                    Log.e("VolleyError", "Message: " + error.getMessage());
                }
        );

        Volley.newRequestQueue(this).add(request);
    }

    private void filterByDay(String day) {
        items.clear();
        for (ScheduleItem item : allItems) {
            if (item.getDay().substring(0, 3).equalsIgnoreCase(day)) {
                items.add(item);
            }
        }

        if (items.isEmpty()) {
            emptyMessage.setVisibility(View.VISIBLE);
            recycler.setVisibility(View.GONE);
        } else {
            emptyMessage.setVisibility(View.GONE);
            recycler.setVisibility(View.VISIBLE);
        }

        adapter.notifyDataSetChanged();
    }

    private void resetButtonsTextColor() {
        int defaultColor = getResources().getColor(android.R.color.black);
        buttonSun.setTextColor(defaultColor);
        buttonMon.setTextColor(defaultColor);
        buttonTue.setTextColor(defaultColor);
        buttonWed.setTextColor(defaultColor);
        buttonThu.setTextColor(defaultColor);
    }

    private void setupBottomNavigation() {
        highlightSelectedTab(2); // Schedule tab selected

        findViewById(R.id.navDashboard).setOnClickListener(v -> {
            highlightSelectedTab(1);
            Intent intent = new Intent(this, StudentDashboardActivity.class); // Or StudentDashboardActivity if used
            intent.putExtra("id", studentId); 
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });

        findViewById(R.id.navSchedule).setOnClickListener(v -> {
            highlightSelectedTab(2);
            // Do nothing; already here
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
}

