package com.example.finalproject.aseel;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;

import org.json.JSONArray;

import java.io.*;
import java.net.*;
import java.util.*;

public class Teacherschedule extends AppCompatActivity {
    Spinner teacherSpinner, subjectSpinner, daySpinner;
    EditText time, endTime, room;
    Button addButton;

    String serverIP = "10.0.2.2:80";
    String loadTeachersURL = "http://" + serverIP + "/MobileProject/getTeacherIds.php";
    String loadSubjectsURL = "http://" + serverIP + "/MobileProject/getSubjectIds.php";
    String insertUrl = "http://" + serverIP + "/MobileProject/addSchedule.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacherschedule);

        teacherSpinner = findViewById(R.id.teacherSpinner);
        subjectSpinner = findViewById(R.id.subjectSpinner);
        daySpinner = findViewById(R.id.daySpinner);
        time = findViewById(R.id.time);
        endTime = findViewById(R.id.endTime);
        room = findViewById(R.id.room);
        addButton = findViewById(R.id.addButton);

        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"});
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(dayAdapter);

        loadTeacherIDs();
        loadSubjectIDs();

        // Map full day names to 3-letter ENUM values
        final Map<String, String> dayMap = new HashMap<>();
        dayMap.put("Sunday", "Sun");
        dayMap.put("Monday", "Mon");
        dayMap.put("Tuesday", "Tue");
        dayMap.put("Wednesday", "Wed");
        dayMap.put("Thursday", "Thu");

        addButton.setOnClickListener(view -> {
            String teacherID = teacherSpinner.getSelectedItem().toString().trim();
            String subjectID = subjectSpinner.getSelectedItem().toString().trim();
            String fullDay = daySpinner.getSelectedItem().toString();
            String dayShort = dayMap.get(fullDay);
            String startTime = time.getText().toString().trim();
            String endTimeStr = endTime.getText().toString().trim();
            String roomStr = room.getText().toString().trim();

            if (teacherID.isEmpty() || subjectID.isEmpty() || dayShort == null ||
                    startTime.isEmpty() || endTimeStr.isEmpty() || roomStr.isEmpty()) {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            new AddScheduleTask().execute(teacherID, dayShort, subjectID, startTime, endTimeStr, roomStr);
        });
    }

    private void loadTeacherIDs() {
        new AsyncTask<Void, Void, List<String>>() {
            @Override
            protected List<String> doInBackground(Void... voids) {
                List<String> ids = new ArrayList<>();
                try {
                    HttpURLConnection conn = (HttpURLConnection) new URL(loadTeachersURL).openConnection();
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
                ArrayAdapter<String> adapter = new ArrayAdapter<>(Teacherschedule.this, android.R.layout.simple_spinner_item, ids);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                teacherSpinner.setAdapter(adapter);
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
                    for (int i = 0; i < jsonArray.length(); i++)
                        subjects.add(jsonArray.getJSONObject(i).getString("id"));
                } catch (Exception e) { e.printStackTrace(); }
                return subjects;
            }
            @Override
            protected void onPostExecute(List<String> subjectIds) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(Teacherschedule.this, android.R.layout.simple_spinner_item, subjectIds);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subjectSpinner.setAdapter(adapter);
            }
        }.execute();
    }

    private class AddScheduleTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                HttpURLConnection conn = (HttpURLConnection) new URL(insertUrl).openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String data = "teacher_id=" + URLEncoder.encode(params[0], "UTF-8") +
                        "&day=" + URLEncoder.encode(params[1], "UTF-8") +
                        "&subject_id=" + URLEncoder.encode(params[2], "UTF-8") +
                        "&start_time=" + URLEncoder.encode(params[3], "UTF-8") +
                        "&end_time=" + URLEncoder.encode(params[4], "UTF-8") +
                        "&room=" + URLEncoder.encode(params[5], "UTF-8");
                writer.write(data); writer.flush(); writer.close(); os.close();
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder(); String line;
                while ((line = in.readLine()) != null) response.append(line);
                in.close(); return response.toString().trim();
            } catch (Exception e) { e.printStackTrace(); return "error"; }
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(Teacherschedule.this, "Added Successfully", Toast.LENGTH_LONG).show();
        }
    }
}
