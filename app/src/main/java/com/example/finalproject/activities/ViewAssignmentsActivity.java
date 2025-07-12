package com.example.finalproject.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.adapters.AssignmentAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalproject.R;
import com.example.finalproject.models.Assignment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewAssignmentsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewAssignments;
    private ArrayList<Assignment> assignmentList = new ArrayList<>();
    private int teacherId = 3;
    private int subjectId;

    private TextView textViewNoAssignments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_assignments);

        recyclerViewAssignments = findViewById(R.id.recyclerViewAssignments);
        recyclerViewAssignments.setLayoutManager(new LinearLayoutManager(this));

        textViewNoAssignments = findViewById(R.id.textViewNoAssignments);
        // Get subjectId from intent
        subjectId = getIntent().getIntExtra("subject_id", -1);

        if (subjectId != -1) {
            loadAssignments();
        } else {
            Toast.makeText(this, "Invalid subject", Toast.LENGTH_SHORT).show();
            finish();
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

    private void loadAssignments() {
        String url = "http://10.0.2.2:80/MobileProject/get_teacher_assignments_for_subject.php?teacher_id=" + teacherId + "&subject_id=" + subjectId;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    assignmentList.clear();
                    if (response.length() == 0) {
                        textViewNoAssignments.setVisibility(View.VISIBLE);
                        recyclerViewAssignments.setVisibility(View.GONE);
                    } else {
                        textViewNoAssignments.setVisibility(View.GONE);
                        recyclerViewAssignments.setVisibility(View.VISIBLE);
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Assignment assignment = new Assignment(
                                        obj.getInt("id"),
                                        obj.getInt("subject_id"),
                                        obj.getInt("teacher_id"),
                                        obj.getString("title"),
                                        obj.getString("description"),
                                        obj.getString("due_date"),
                                        obj.getString("attachment_url"),
                                        obj.optString("subject_name", "")
                                );
                                assignmentList.add(assignment);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        recyclerViewAssignments.setAdapter(new AssignmentAdapter(ViewAssignmentsActivity.this, assignmentList));
                    }},
                error -> Toast.makeText(this, "Failed to load assignments", Toast.LENGTH_SHORT).show()
        );

        Volley.newRequestQueue(this).add(request);
    }


}
