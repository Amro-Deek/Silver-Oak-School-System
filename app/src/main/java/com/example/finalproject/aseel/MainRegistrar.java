package com.example.finalproject.aseel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalproject.R;

public class MainRegistrar extends AppCompatActivity {

    Button btnAddStudent,btnAddSubject, btnEditStudent, btnAddTeacher, btnEditTeacher, btnStudentSchedule, btnTeacherSchedule;
    LinearLayout navDashboard, navSchedule, navMarks;
    private  int teacherId ;

    private SharedPreferences prefs ;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_registrar);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_registrar), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        teacherId= prefs.getInt("id",-1);
       // teacherId = getIntent().getIntExtra("id",-1);
        // Buttons
        btnAddStudent = findViewById(R.id.btnAddStudent);
        btnEditStudent = findViewById(R.id.btnEditStudent);
        btnAddTeacher = findViewById(R.id.btnAddTeacher);
        btnEditTeacher = findViewById(R.id.btnEditTeacher);
        btnStudentSchedule = findViewById(R.id.btnStudentSchedule);
        btnTeacherSchedule = findViewById(R.id.btnTeacherSchedule);


        // Button click listeners
        btnAddStudent.setOnClickListener(v -> startActivity(new Intent(this, AddStudentActivity.class)));
        btnEditStudent.setOnClickListener(v -> startActivity(new Intent(this, editstudent.class)));
        btnAddTeacher.setOnClickListener(v -> startActivity(new Intent(this, AddTeacher.class)));
        btnEditTeacher.setOnClickListener(v -> startActivity(new Intent(this, editteacher.class)));
        btnStudentSchedule.setOnClickListener(v -> startActivity(new Intent(this, studentschedule.class)));
        btnTeacherSchedule.setOnClickListener(v -> startActivity(new Intent(this, Teacherschedule.class)));
        btnAddSubject = findViewById(R.id.btnAddSubject);

        btnAddSubject.setOnClickListener(v -> startActivity(new Intent(this, AddSubjectActivity.class)));

        // Bottom navigation

    }
}
