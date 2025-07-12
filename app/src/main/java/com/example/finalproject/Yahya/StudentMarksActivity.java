package com.example.finalproject.Yahya;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
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
import com.example.finalproject.Yahya.StudentScheduleActivity;
import com.example.finalproject.Yahya.SubjectMarksAdapter;
import com.example.finalproject.Yahya.ThemeUtils;
import com.example.finalproject.deema.MainActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentMarksActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SubjectMarksAdapter adapter;

    private List<RawMark> originalMarksList = new ArrayList<>();
    private List<SubjectMarksAdapter.SubjectWithMarks> subjectList = new ArrayList<>();

    private int studentId = -1; // No static ID now
    private static final String BASE_URL = "http://10.0.2.2:80/MobileProject/getStudentMarks.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtils.applyTheme(this);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_marks);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ✅ Get student ID from intent
        studentId = getIntent().getIntExtra("id", -1);
        if (studentId == -1) {
            Toast.makeText(this, "Student ID missing", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SubjectMarksAdapter(this, subjectList);
        recyclerView.setAdapter(adapter);

        setupBottomNavigation();
        fetchMarks();
    }

    private void setupBottomNavigation() {
        highlightSelectedTab(3); // 3 = Marks

        findViewById(R.id.navDashboard).setOnClickListener(v -> {
            highlightSelectedTab(1);
            Intent intent = new Intent(this, StudentDashboardActivity.class);
            intent.putExtra("id", studentId); 
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
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
            // Already on Marks, but we still recreate
            Intent intent = new Intent(this, StudentMarksActivity.class);
            intent.putExtra("id", studentId); 
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        });
    }

    private void fetchMarks() {
        String url = BASE_URL + "?student_id=" + studentId;
        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray array = new JSONArray(response);
                        originalMarksList.clear();

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            String subject = obj.getString("subject_name");
                            String examType = obj.getString("exam_type");
                            double mark = obj.getDouble("mark");

                            originalMarksList.add(new RawMark(subject, examType, mark));
                        }

                        groupBySubjectAndDisplay();
                    } catch (Exception e) {
                        Toast.makeText(this, "Parsing error", Toast.LENGTH_SHORT).show();
                        Log.e("ParseError", e.toString());
                    }
                },
                error -> {
                    Toast.makeText(this, "Network error", Toast.LENGTH_SHORT).show();
                    Log.e("VolleyError", error.toString());
                });

        Volley.newRequestQueue(this).add(request);
    }

    private void groupBySubjectAndDisplay() {
        subjectList.clear();
        Map<String, List<SubjectMarksAdapter.Mark>> grouped = new HashMap<>();

        for (RawMark mark : originalMarksList) {
            grouped
                    .computeIfAbsent(mark.subject, k -> new ArrayList<>())
                    .add(new SubjectMarksAdapter.Mark(mark.examType, mark.mark));
        }

        for (Map.Entry<String, List<SubjectMarksAdapter.Mark>> entry : grouped.entrySet()) {
            subjectList.add(new SubjectMarksAdapter.SubjectWithMarks(entry.getKey(), entry.getValue()));
        }

        adapter.notifyDataSetChanged();
    }

    static class RawMark {
        String subject, examType;
        double mark;

        RawMark(String subject, String examType, double mark) {
            this.subject = subject;
            this.examType = examType;
            this.mark = mark;
        }
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
