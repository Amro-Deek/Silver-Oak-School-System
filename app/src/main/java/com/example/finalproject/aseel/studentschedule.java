package com.example.finalproject.aseel;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.finalproject.R;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.net.*;
import java.util.*;

public class studentschedule extends AppCompatActivity {

    Spinner studentIdSpinner, subjectIdSpinner, daySpinner;
    EditText startTimeInput, endTimeInput, locationInput;
    Button createScheduleBtn;

    String serverIP = "10.0.2.2:80";
    String loadStudentsURL = "http://" + serverIP + "/MobileProject/getStudentIds.php";
    String loadSubjectsURL = "http://" + serverIP + "/MobileProject/getSubjectIds.php";
    String submitURL = "http://" + serverIP + "/MobileProject/studentSchedual.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentschedule);

        studentIdSpinner = findViewById(R.id.inputStudentId);
        subjectIdSpinner = findViewById(R.id.inputSubjectId);
        daySpinner = findViewById(R.id.inputDay);
        startTimeInput = findViewById(R.id.inputStartTime);
        endTimeInput = findViewById(R.id.inputEndTime);
        locationInput = findViewById(R.id.inputLocation);
        createScheduleBtn = findViewById(R.id.btnCreateSchedule);

        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"});
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(dayAdapter);

        loadStudentIDs();
        loadSubjectIDs();

        final Map<String, String> dayMap = new HashMap<>();
        dayMap.put("Sunday", "Sun");
        dayMap.put("Monday", "Mon");
        dayMap.put("Tuesday", "Tue");
        dayMap.put("Wednesday", "Wed");
        dayMap.put("Thursday", "Thu");

        createScheduleBtn.setOnClickListener(v -> {
            String studentId = studentIdSpinner.getSelectedItem().toString();
            String subjectId = subjectIdSpinner.getSelectedItem().toString();
            String fullDay = daySpinner.getSelectedItem().toString();
            String day = dayMap.get(fullDay);
            String start = startTimeInput.getText().toString();
            String end = endTimeInput.getText().toString();
            String location = locationInput.getText().toString();

            if (studentId.isEmpty() || subjectId.isEmpty() || day == null || start.isEmpty() || end.isEmpty()) {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            new SubmitScheduleTask().execute(studentId, subjectId, day, start, end, location);
        });
    }

    private void loadStudentIDs() {
        new AsyncTask<Void, Void, List<String>>() {
            @Override
            protected List<String> doInBackground(Void... voids) {
                List<String> ids = new ArrayList<>();
                try {
                    HttpURLConnection conn = (HttpURLConnection) new URL(loadStudentsURL).openConnection();
                    conn.setRequestMethod("GET");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder json = new StringBuilder(); String line;
                    while ((line = reader.readLine()) != null) json.append(line);
                    reader.close();
                    JSONArray jsonArray = new JSONArray(json.toString());
                    for (int i = 0; i < jsonArray.length(); i++) ids.add(jsonArray.getString(i));
                } catch (Exception e) { e.printStackTrace(); }
                return ids;
            }

            @Override
            protected void onPostExecute(List<String> ids) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(studentschedule.this, android.R.layout.simple_spinner_item, ids);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                studentIdSpinner.setAdapter(adapter);
            }
        }.execute();
    }

    private void loadSubjectIDs() {
        new AsyncTask<Void, Void, List<String>>() {
            @Override
            protected List<String> doInBackground(Void... voids) {
                List<String> subjects = new ArrayList<>();
                try {
                    HttpURLConnection conn = (HttpURLConnection) new URL(loadSubjectsURL).openConnection();
                    conn.setRequestMethod("GET");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder json = new StringBuilder(); String line;
                    while ((line = reader.readLine()) != null) json.append(line);
                    reader.close();
                    JSONArray jsonArray = new JSONArray(json.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        subjects.add(obj.getString("id"));
                    }
                } catch (Exception e) { e.printStackTrace(); }
                return subjects;
            }

            @Override
            protected void onPostExecute(List<String> ids) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(studentschedule.this, android.R.layout.simple_spinner_item, ids);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subjectIdSpinner.setAdapter(adapter);
            }
        }.execute();
    }

    private class SubmitScheduleTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(submitURL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                String data = "student_id=" + URLEncoder.encode(params[0], "UTF-8") +
                        "&subject_id=" + URLEncoder.encode(params[1], "UTF-8") +
                        "&day_of_week=" + URLEncoder.encode(params[2], "UTF-8") +
                        "&start_time=" + URLEncoder.encode(params[3], "UTF-8") +
                        "&end_time=" + URLEncoder.encode(params[4], "UTF-8") +
                        "&location=" + URLEncoder.encode(params[5], "UTF-8");

                writer.write(data); writer.flush(); writer.close(); os.close();

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder(); String line;
                while ((line = in.readLine()) != null) response.append(line);
                in.close(); return response.toString().trim();

            } catch (Exception e) {
                e.printStackTrace();
                return "error";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(studentschedule.this, "Added Successfully", Toast.LENGTH_LONG).show();
        }
    }
}
