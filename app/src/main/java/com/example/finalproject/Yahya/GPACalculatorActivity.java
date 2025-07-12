package com.example.finalproject.Yahya;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;

import com.example.finalproject.R;

import java.util.ArrayList;

public class GPACalculatorActivity extends AppCompatActivity {

    private EditText edtSubject, edtMark;
    private Button btnAdd, btnCalculate;
    private ListView listSubjects;
    private TextView txtGpaResult;

    private final ArrayList<String> subjectList = new ArrayList<>();
    private final ArrayList<Integer> markList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gpacalculator);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtSubject = findViewById(R.id.edtSubject);
        edtMark = findViewById(R.id.edtMark);
        btnAdd = findViewById(R.id.btnAdd);
        btnCalculate = findViewById(R.id.btnCalculate);
        listSubjects = findViewById(R.id.listSubjects);
        txtGpaResult = findViewById(R.id.txtGpaResult);

        prefs = getSharedPreferences("gpa_data", MODE_PRIVATE);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, subjectList);
        listSubjects.setAdapter(adapter);

        restoreUnsavedInputs();

        btnAdd.setOnClickListener(view -> {
            String subject = edtSubject.getText().toString().trim();
            String markStr = edtMark.getText().toString().trim();

            if (subject.isEmpty() || markStr.isEmpty()) {
                Toast.makeText(this, "Please enter both subject and mark", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int mark = Integer.parseInt(markStr);
                if (mark < 0 || mark > 100) {
                    Toast.makeText(this, "Mark must be between 0 and 100", Toast.LENGTH_SHORT).show();
                    return;
                }

                subjectList.add(subject + ": " + mark);
                markList.add(mark);
                adapter.notifyDataSetChanged();

                edtSubject.setText("");
                edtMark.setText("");

                saveUnsavedInputs();

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid mark", Toast.LENGTH_SHORT).show();
            }
        });

        btnCalculate.setOnClickListener(view -> {
            if (markList.isEmpty()) {
                Toast.makeText(this, "No subjects added", Toast.LENGTH_SHORT).show();
                return;
            }

            double total = 0;
            for (int mark : markList) {
                total += mark;
            }

            double gpa = total / markList.size();
            txtGpaResult.setText(String.format("GPA: %.2f", gpa));
            txtGpaResult.setVisibility(View.VISIBLE);

            clearUnsavedInputs();
        });
    }

    private void saveUnsavedInputs() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("subject_count", subjectList.size());
        for (int i = 0; i < subjectList.size(); i++) {
            editor.putString("subject_" + i, subjectList.get(i));
            editor.putInt("mark_" + i, markList.get(i));
        }
        editor.apply();
    }

    private void restoreUnsavedInputs() {
        int count = prefs.getInt("subject_count", 0);
        for (int i = 0; i < count; i++) {
            String subjectItem = prefs.getString("subject_" + i, null);
            int mark = prefs.getInt("mark_" + i, -1);
            if (subjectItem != null && mark != -1) {
                subjectList.add(subjectItem);
                markList.add(mark);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void clearUnsavedInputs() {
        prefs.edit().clear().apply();
    }
}