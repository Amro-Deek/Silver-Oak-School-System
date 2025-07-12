package com.example.finalproject.activities;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalproject.R;

import com.example.finalproject.adapters.SubjectAdapter;
import com.example.finalproject.models.*;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeacherDashboardActivity extends AppCompatActivity {

    private RecyclerView subjectsRecyclerView;
    private List<Subject> subjects = new ArrayList<>();
    private SubjectAdapter subjectAdapter;
    private  int teacherId ;

    private Switch themeSwitch;

    private SharedPreferences prefs ;

    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard); // This should match your XML filename

        subjectsRecyclerView = findViewById(R.id.subjectsRecyclerView);
        subjectsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        subjectAdapter = new SubjectAdapter(this, subjects);
        subjectsRecyclerView.setAdapter(subjectAdapter);
        prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        teacherId= prefs.getInt("id",-1);
      //  teacherId = getIntent().getIntExtra("id",-1);
        // Optional: check if it was received correctly
        if (teacherId != -1) {
            Toast.makeText(this, "Received Teacher ID: " + teacherId, Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Teacher ID not received!", Toast.LENGTH_SHORT).show();
        }
        findViewById(R.id.navDashboard).setOnClickListener(v -> {
            // Optionally refresh or scroll to top
            highlightSelectedTab(1);
            startActivity(new Intent(this, TeacherDashboardActivity.class));

        });

        findViewById(R.id.navSchedule).setOnClickListener(v -> {
            // Replace with your actual schedule activity
            startActivity(new Intent(this, TeacherScheduleActivity.class));
            highlightSelectedTab(2);
        });

        findViewById(R.id.navMarks).setOnClickListener(v -> {
            // Replace with your actual marks/assignments activity
            highlightSelectedTab(3);
            startActivity(new Intent(this, UploadMarksActivity.class));

        });
        highlightSelectedTab(1);// this for the current page
        themeSwitch = findViewById(R.id.themeSwitch);
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        boolean isDarkMode = currentNightMode == Configuration.UI_MODE_NIGHT_YES;
        editor = prefs.edit();
        editor.putBoolean("dark_mode", isDarkMode);  // isChecked is true for dark mode
        editor.apply();

// Set switch initial state
        themeSwitch.setChecked(isDarkMode);

// Handle switch toggle
        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {


            // Save preference
            editor = prefs.edit();
            editor.putBoolean("dark_mode", isChecked);
            editor.apply();
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }

            // Optional: recreate the activity to apply theme immediately
            recreate();
        });
        loadSubjects();
    }



    private void loadSubjects() {
        String url = "http://10.0.2.2:80/MobileProject/get_teacher_subjects.php?teacher_id=" + teacherId;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    subjects.clear();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            Subject subject = new Subject(obj.getInt("id"), obj.getString("name"));
                            subjects.add(subject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    subjectAdapter.notifyDataSetChanged();
                },
                error -> Toast.makeText(this, "Failed to load subjects", Toast.LENGTH_SHORT).show()
        );

        queue.add(request);
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