// UploadMarksActivity.java (merged functionality: publish + update + collapsible + search)
package com.example.finalproject.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalproject.R;
import com.example.finalproject.models.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class UploadMarksActivity extends AppCompatActivity {

    Spinner spinnerSubjects;
    EditText editTextSearch, editTextExamType;
    LinearLayout studentListContainer, studentMarksContainer;
    List<Subject> subjects = new ArrayList<>();
    List<Student> allStudents = new ArrayList<>();
    Map<Integer, List<Mark>> studentMarksMap = new HashMap<>();
    Map<Integer, EditText> marksInputs = new HashMap<>();
    Map<Integer, EditText> newMarkInputs = new HashMap<>();
    int selectedSubjectId = -1;
    int teacherId = 3;

    private Button buttonSubmitMarks;

    private EditText editTextFullMark;

    private SharedPreferences prefs ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_marks);

        spinnerSubjects = findViewById(R.id.spinnerSubjects);
        editTextSearch = findViewById(R.id.editTextSearch);
        editTextExamType = findViewById(R.id.editTextExamType);
        studentListContainer = findViewById(R.id.studentListContainer);
        studentMarksContainer = findViewById(R.id.studentMarksContainer);
        buttonSubmitMarks = findViewById(R.id.buttonSubmitMarks);
        editTextFullMark = findViewById(R.id.editTextFullMark);
        editTextFullMark.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editTextFullMark.setFilters(new InputFilter[]{ getMarkRangeFilter() });
        prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        teacherId= prefs.getInt("id",-1);


        int selectedColor = Color.parseColor("#d90429");
        TextView t3 = findViewById(R.id.navMarks).findViewById(R.id.text_marks);
        t3.setTextColor(selectedColor);
        findViewById(R.id.navDashboard).setOnClickListener(v -> {
            // Optionally refresh or scroll to top
            startActivity(new Intent(this, TeacherDashboardActivity.class));

        });

        findViewById(R.id.navSchedule).setOnClickListener(v -> {
            // Replace with your actual schedule activity
             startActivity(new Intent(this, TeacherScheduleActivity.class));
        });

        findViewById(R.id.navMarks).setOnClickListener(v -> {
            // Replace with your actual marks/assignments activity
            startActivity(new Intent(this, UploadMarksActivity.class));
        });
        loadSubjects();

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                displayStudentsFiltered(s.toString());
            }
        });

        buttonSubmitMarks.setOnClickListener(v -> submitMarks());
    }

    private void loadSubjects() {
        String url = "http://10.0.2.2:80/MobileProject/get_teacher_subjects.php?teacher_id=" + teacherId;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    List<String> subjectNames = new ArrayList<>();
                    subjects.clear();

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            Subject subject = new Subject(obj.getInt("id"), obj.getString("name"));
                            subjects.add(subject);
                            subjectNames.add(subject.getName());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subjectNames);
                    subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerSubjects.setAdapter(subjectAdapter);

                    if (!subjects.isEmpty()) {
                        selectedSubjectId = subjects.get(0).getId();
                        spinnerSubjects.setSelection(0);
                        loadStudentsForSubject(selectedSubjectId);
                        loadStudentsWithMarks();
                    }

                    spinnerSubjects.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectedSubjectId = subjects.get(position).getId();
                            loadStudentsForSubject(selectedSubjectId);
                            loadStudentsWithMarks();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {}
                    });
                },
                error -> Toast.makeText(this, "Failed to load subjects", Toast.LENGTH_SHORT).show()
        );

        queue.add(request);
    }

    private void loadStudentsForSubject(int subjectId) {
        String url = "http://10.0.2.2:80/MobileProject/get_students_by_subject.php?subject_id=" + subjectId;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    studentMarksContainer.removeAllViews();
                    marksInputs.clear();

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            int studentId = obj.getInt("id");
                            String studentName = obj.getString("name");

                            LinearLayout row = new LinearLayout(this);
                            row.setOrientation(LinearLayout.HORIZONTAL);
                            row.setPadding(0, 12, 0, 12);
                            row.setLayoutParams(new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT));

                            TextView nameView = new TextView(this);
                            nameView.setText(studentName);
                            nameView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

                            EditText markInput = new EditText(this);
                            markInput.setHint("Mark");
                            markInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                            markInput.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
                            markInput.setFilters(new InputFilter[]{getMarkRangeFilter()});
                            markInput.setBackgroundResource(R.drawable.edit_text_border);

                            row.addView(nameView);
                            row.addView(markInput);

                            studentMarksContainer.addView(row);
                            marksInputs.put(studentId, markInput);
                            newMarkInputs.put(studentId, markInput);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                error -> {
                    Toast.makeText(this, "Failed to load students", Toast.LENGTH_SHORT).show();
                    Log.e("STUDENT_ERROR", error.toString());
                });

        queue.add(request);
    }


    private void loadStudentsWithMarks() {
        String url = "http://10.0.2.2:80/MobileProject/get_students_with_marks.php?subject_id=" + selectedSubjectId;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    allStudents.clear();
                    studentMarksMap.clear();
                    studentListContainer.removeAllViews();

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            int studentId = obj.getInt("student_id");
                            String studentName = obj.getString("student_name");
                            Student student = new Student(studentId, studentName);
                            allStudents.add(student);

                            JSONArray marksArray = obj.getJSONArray("marks");
                            List<Mark> marks = new ArrayList<>();
                            for (int j = 0; j < marksArray.length(); j++) {
                                JSONObject markObj = marksArray.getJSONObject(j);
                                marks.add(new Mark(
                                        markObj.getInt("id"),
                                        markObj.getString("exam_type"),
                                        markObj.getDouble("mark")
                                ));
                            }
                            studentMarksMap.put(studentId, marks);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    displayStudentsFiltered(editTextSearch.getText().toString());
                },
                error -> Toast.makeText(this, "Failed to load students/marks", Toast.LENGTH_SHORT).show()
        );

        queue.add(request);
    }

    private void displayStudentsFiltered(String query) {
        studentListContainer.removeAllViews();
        newMarkInputs.clear();

        for (Student student : allStudents) {
            if (!student.getName().toLowerCase().contains(query.toLowerCase())) continue;

            TextView studentHeader = new TextView(this);
            studentHeader.setText("▼ " + student.getName());
            studentHeader.setTextSize(16);
            studentHeader.setPadding(0, 12, 0, 12);
            studentHeader.setTypeface(null, android.graphics.Typeface.BOLD);

            LinearLayout marksContainer = new LinearLayout(this);
            marksContainer.setOrientation(LinearLayout.VERTICAL);
            marksContainer.setVisibility(View.GONE);

            studentHeader.setOnClickListener(v -> {
                boolean expanded = marksContainer.getVisibility() == View.VISIBLE;
                marksContainer.setVisibility(expanded ? View.GONE : View.VISIBLE);
                studentHeader.setText((expanded ? "▼ " : "▲ ") + student.getName());
            });

            for (Mark mark : studentMarksMap.get(student.getId())) {
                LinearLayout row = new LinearLayout(this);
                row.setOrientation(LinearLayout.HORIZONTAL);
                row.setPadding(0, 8, 0, 8);

                TextView label = new TextView(this);
                label.setText(mark.getExamType());
                label.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

                EditText input = new EditText(this);
                input.setText(String.valueOf(mark.getMark()));
                input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                input.setFilters(new InputFilter[]{ getMarkRangeFilter() });
                input.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

                Button saveBtn = new Button(this);
                saveBtn.setText("Save");
                saveBtn.setOnClickListener(v -> {
                    String newVal = input.getText().toString().trim();
                    if (newVal.isEmpty()) {
                        Toast.makeText(this, "Mark cannot be empty", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    new AlertDialog.Builder(this)
                            .setTitle("Update Mark")
                            .setMessage("Are you sure you want to update this mark?")
                            .setPositiveButton("Yes", (dialog, which) -> updateMark(mark.getId(), newVal))
                            .setNegativeButton("Cancel", null)
                            .show();
                });

                row.addView(label);
                row.addView(input);
                row.addView(saveBtn);
                marksContainer.addView(row);
            }

            // Optional: add a field to submit a new mark for this student (if needed)


            studentListContainer.addView(studentHeader);
            studentListContainer.addView(marksContainer);
        }
    }


    private void updateMark(int markId, String newMark) {
        String url = "http://10.0.2.2:80/MobileProject/update_mark.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> Toast.makeText(this, "Mark updated", Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show()) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("mark_id", String.valueOf(markId));
                params.put("mark", newMark);
                return params;
            }
        };

        queue.add(request);
    }
    private void submitMarks() {
        String examType = editTextExamType.getText().toString().trim();
        String fullMarkStr = editTextFullMark.getText().toString().trim();

        if (selectedSubjectId == -1 || examType.isEmpty() || fullMarkStr.isEmpty()) {
            Toast.makeText(this, "Please select subject, enter exam type and full mark", Toast.LENGTH_SHORT).show();
            return;
        }

        double fullMark = Double.parseDouble(fullMarkStr);

        String url = "http://10.0.2.2:80/MobileProject/submit_marks.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        boolean atLeastOne = false;

        for (Map.Entry<Integer, EditText> entry : marksInputs.entrySet()) {
            final int studentId = entry.getKey();
            final String markStr = entry.getValue().getText().toString().trim();

            if (markStr.isEmpty()) continue;

            final double mark = Double.parseDouble(markStr);
            atLeastOne = true;

            StringRequest request = new StringRequest(Request.Method.POST, url,
                    response -> {
                        Log.d("MARK_SUBMIT", "Response: " + response);
                        if (!response.trim().equals("OK")) {
                            Toast.makeText(this, "Failed to submit for student " + studentId, Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        Log.e("MARK_SUBMIT", "Error: " + error.toString());
                        Toast.makeText(this, "Network error", Toast.LENGTH_SHORT).show();
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("student_id", String.valueOf(studentId));
                    params.put("subject_id", String.valueOf(selectedSubjectId));
                    params.put("exam_type", examType);
                    params.put("mark", String.valueOf(mark));
                    params.put("full_mark", String.valueOf(fullMark)); // NEW LINE
                    return params;
                }
            };

            queue.add(request);
        }

        if (atLeastOne) {
            Toast.makeText(this, "Submitting marks...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No marks to submit", Toast.LENGTH_SHORT).show();
        }
    }

    private InputFilter getMarkRangeFilter() {
        return (source, start, end, dest, dstart, dend) -> {
            try {
                String input = dest.toString().substring(0, dstart) + source.toString() + dest.toString().substring(dend);
                if (input.isEmpty()) return null;
                double val = Double.parseDouble(input);
                if (val >= 0 && val <= 100) return null;
            } catch (NumberFormatException ignored) {}
            return "";
        };
    }






}
