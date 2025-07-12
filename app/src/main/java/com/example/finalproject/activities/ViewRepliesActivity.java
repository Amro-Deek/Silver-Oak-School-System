package com.example.finalproject.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalproject.R;
import com.example.finalproject.adapters.AssignmentResponseAdapter;
import com.example.finalproject.models.AssignmentResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewRepliesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<AssignmentResponse> replies = new ArrayList<>();
    private int assignmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_replies);

        recyclerView = findViewById(R.id.recyclerViewReplies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        assignmentId = getIntent().getIntExtra("assignment_id", -1);
        if (assignmentId != -1) {
            loadReplies();
        } else {
            Toast.makeText(this, "Invalid assignment", Toast.LENGTH_SHORT).show();
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

    private void loadReplies() {
        String url = "http://10.0.2.2:80/MobileProject/get_replies_by_assignment.php?assignment_id=" + assignmentId;

        TextView textViewNoReplies = findViewById(R.id.textViewNoReplies);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    replies.clear();

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            replies.add(new AssignmentResponse(
                                    obj.getString("student_name"),
                                    obj.getString("submission_date"),
                                    obj.optString("attachment_url", "No file"),
                                    obj.optString("message", "No message")
                            ));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    if (replies.isEmpty()) {
                        textViewNoReplies.setVisibility(View.VISIBLE);
                    } else {
                        textViewNoReplies.setVisibility(View.GONE);
                    }

                    recyclerView.setAdapter(new AssignmentResponseAdapter(ViewRepliesActivity.this, replies));
                },
                error -> Toast.makeText(this, "Failed to load replies", Toast.LENGTH_SHORT).show());

        Volley.newRequestQueue(this).add(request);
    }

}
