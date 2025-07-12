package com.example.finalproject.Yahya;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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

public class ViewAssigmentsActivity extends AppCompatActivity {

    private List<Assigment> assignmentList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AssignmentAdapter adapter;
    private int studentId = -1;
    private static final String BASE_URL = "http://10.0.2.2:80/MobileProject/getStudentAssignments.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_assigments);

        // ✅ Get studentId from intent
        studentId = getIntent().getIntExtra("id", -1);
        if (studentId == -1) {
            Toast.makeText(this, "Missing student ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.assignmentRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AssignmentAdapter(this, assignmentList, studentId); // if needed in adapter
        recyclerView.setAdapter(adapter);

        fetchAssignments();
    }

    private void fetchAssignments() {
        String url = BASE_URL + "?student_id=" + studentId;
        Log.d("fetchAssignments", "Requesting URL: " + url);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    Log.d("fetchAssignments", "Raw response: " + response);

                    try {
                        JSONArray array = new JSONArray(response);
                        assignmentList.clear();

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);

                            String id = obj.getString("assignment_id");
                            String title = obj.getString("title");
                            String subject = obj.getString("subject_name");
                            String dueDate = obj.getString("due_date");
                            String status = "Not Submitted";
                            String description = obj.getString("description");
                            String attachmentUrl = obj.getString("attachment_url");

                            Assigment assignment = new Assigment(id, title, subject, dueDate, status, description, attachmentUrl);
                            assignmentList.add(assignment);
                        }

                        adapter.notifyDataSetChanged();
                        Log.i("fetchAssignments", "Assignments loaded successfully. Total: " + assignmentList.size());

                    } catch (JSONException e) {
                        Log.e("fetchAssignments", "JSON parsing error: " + e.getMessage(), e);
                    }
                },
                error -> {
                    Log.e("fetchAssignments", "Volley error occurred");
                    if (error.networkResponse != null) {
                        int statusCode = error.networkResponse.statusCode;
                        String responseBody = new String(error.networkResponse.data);
                        Log.e("fetchAssignments", "Status Code: " + statusCode + ", Response: " + responseBody);
                    } else {
                        Log.e("fetchAssignments", "Error message: " + error.getMessage());
                    }
                }
        );

        Volley.newRequestQueue(this).add(request);
    }


}
