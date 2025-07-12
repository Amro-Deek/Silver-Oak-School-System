// TeacherScheduleActivity.java
package com.example.finalproject.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalproject.R;
import com.example.finalproject.adapters.ScheduleAdapter;
import com.example.finalproject.models.ScheduleEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TeacherScheduleActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ScheduleAdapter scheduleAdapter;
    private List<ScheduleEntry> scheduleList = new ArrayList<>();
    private int teacherId = 3; // or from shared preferences
    private String selectedDay = "Sun";

    private Button buttonSun, buttonMon, buttonTue, buttonWed, buttonThu;


    private TextView emptyMessage;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_schedule);

        recyclerView = findViewById(R.id.recyclerViewSchedule);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        scheduleAdapter = new ScheduleAdapter(scheduleList);
        recyclerView.setAdapter(scheduleAdapter);

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
        highlightSelectedTab(2);
        emptyMessage = findViewById(R.id.emptyMessage); // Make sure this TextView exists in XML

        buttonSun = findViewById(R.id.buttonSun);
        buttonMon = findViewById(R.id.buttonMon);
        buttonTue = findViewById(R.id.buttonTue);
        buttonWed = findViewById(R.id.buttonWed);
        buttonThu = findViewById(R.id.buttonThu);
        // Load default day
        loadSchedule("Sun");
        highlightDayButton(buttonSun);




        buttonSun.setOnClickListener(v -> {
            loadSchedule("Sun");
            highlightDayButton(buttonSun);
        });
        buttonMon.setOnClickListener(v -> {
            loadSchedule("Mon");
            highlightDayButton(buttonMon);
        });
        buttonTue.setOnClickListener(v -> {
            loadSchedule("Tue");
            highlightDayButton(buttonTue);
        });
        buttonWed.setOnClickListener(v -> {
            loadSchedule("Wed");
            highlightDayButton(buttonWed);
        });
        buttonThu.setOnClickListener(v -> {
            loadSchedule("Thu");
            highlightDayButton(buttonThu);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void loadSchedule(String day) {
        selectedDay = day;
        emptyMessage.setVisibility(View.GONE);

        String url = "http://10.0.2.2:80/MobileProject/get_schedule_for_teacher.php?teacher_id=" + teacherId + "&day=" + day;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    scheduleList.clear();
                    DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
                    DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("H:mm");
                    if (response.length() == 0) {
                        emptyMessage.setText("No schedule for this day.");
                        emptyMessage.setVisibility(View.VISIBLE);
                        return;
                    }
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);

                            // Parse and format times
                            String rawStart = obj.getString("start_time");
                            String rawEnd = obj.getString("end_time");

                            String formattedStart = LocalTime.parse(rawStart, inputFormat).format(outputFormat);
                            String formattedEnd = LocalTime.parse(rawEnd, inputFormat).format(outputFormat);

                            scheduleList.add(new ScheduleEntry(
                                    obj.getString("subject_name"),
                                    obj.getString("room"),
                                    obj.getString("day"),
                                    formattedStart,
                                    formattedEnd
                            ));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    scheduleAdapter.notifyDataSetChanged();
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this,
                            "Error: " + error.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
        );

        Volley.newRequestQueue(this).add(request);
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

    private void highlightDayButton(Button selectedButton) {
        Button[] allButtons = {buttonSun, buttonMon, buttonTue, buttonWed, buttonThu};
        for (Button btn : allButtons) {
            btn.setBackgroundColor(Color.parseColor("#8d99ae"));
            btn.setTextColor(Color.parseColor("#000000"));
        }
        selectedButton.setBackgroundColor(Color.parseColor("#2b2d42"));
        selectedButton.setTextColor(Color.parseColor("#ffffff"));
    }

}
