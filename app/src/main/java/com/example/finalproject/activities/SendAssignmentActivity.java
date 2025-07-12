package com.example.finalproject.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.*;

import android.graphics.drawable.GradientDrawable;
import android.widget.EditText;
import android.graphics.Color;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.finalproject.R;
import com.example.finalproject.network.VolleyMultipartRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SendAssignmentActivity extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST = 1;
    private Uri selectedFileUri;
    private EditText editTextTitle, editTextDescription;
    private TextView textViewDueDate, textViewFileStatus;
    private Button buttonChooseFile, buttonPickDate, buttonSubmit;
    private String selectedDate = "";
    private int subjectId = 1;  // get it from Intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_assignment);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        textViewDueDate = findViewById(R.id.textViewDueDate);
        textViewFileStatus = findViewById(R.id.textViewFileName);  // Make sure this TextView exists in your layout!
        buttonChooseFile = findViewById(R.id.buttonChooseFile);
        buttonPickDate = findViewById(R.id.buttonPickDate);
        buttonSubmit = findViewById(R.id.buttonSubmitAssignment);


        GradientDrawable shape = new GradientDrawable();
        shape.setColor(Color.WHITE); // background color
        shape.setStroke(1, Color.parseColor("#CCCCCC")); // border width & color
        shape.setCornerRadius(8 * getResources().getDisplayMetrics().density); // radius in pixels



        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isDarkMode = prefs.getBoolean("dark_mode", false);
        if (isDarkMode){
            editTextTitle.setBackground(shape);
            editTextDescription.setBackground(shape);
            RelativeLayout rootLayout = findViewById(R.id.rootLayout);
            rootLayout.setBackgroundColor(Color.parseColor("#2b2d42"));
        }
        subjectId = getIntent().getIntExtra("subject_id", -1);

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
        buttonPickDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                textViewDueDate.setText("Due Date: " + selectedDate);
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        buttonChooseFile.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("application/pdf"); // Restrict to PDF files only
            startActivityForResult(Intent.createChooser(intent, "Select PDF File"), PICK_FILE_REQUEST);
        });

        buttonSubmit.setOnClickListener(v -> {
            if (selectedFileUri == null) {
                Toast.makeText(this, "Please select a PDF file first", Toast.LENGTH_SHORT).show();
                return;
            }
            if (editTextTitle.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Please enter the assignment title", Toast.LENGTH_SHORT).show();
                return;
            }
            if (editTextDescription.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Please enter the assignment description", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selectedDate.isEmpty()) {
                Toast.makeText(this, "Please select a due date", Toast.LENGTH_SHORT).show();
                return;
            }
            uploadAssignment(selectedFileUri,
                    3,  // hardcoded teacher_id, replace with actual teacher id if available
                    subjectId,
                    editTextTitle.getText().toString().trim(),
                    editTextDescription.getText().toString().trim(),
                    selectedDate);
        });
    }



    private void uploadAssignment(Uri fileUri, int teacherId, int subjectId, String title,
                                  String description, String dueDate) {
        try {
            final byte[] fileBytes = getBytes(fileUri);
            final String fileName = getFileName(fileUri);

            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST,
                    "http://10.0.2.2/MobileProject/upload_assignment_pdf.php",
                    response -> {
                        try {
                            String resp = new String(response.data);
                            JSONObject jsonObject = new JSONObject(resp);
                            if (jsonObject.optBoolean("success", false)) {
                                Toast.makeText(getApplicationContext(), "Upload successful!", Toast.LENGTH_SHORT).show();
                                textViewFileStatus.setText("Uploaded: " + fileName);
                            } else {
                                Toast.makeText(getApplicationContext(), "Upload failed!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Response parsing error!", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        String message = "Upload error";
                        if (error instanceof VolleyError) {
                            message = error.getMessage();
                        }
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        Log.e("UPLOAD_ERROR", error.toString());
                    }) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("teacher_id", String.valueOf(teacherId));
                    params.put("subject_id", String.valueOf(subjectId));
                    params.put("title", title);
                    params.put("description", description);
                    params.put("due_date", dueDate);
                    return params;
                }

                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();
                    params.put("file", new DataPart(fileName, fileBytes, "application/pdf"));
                    return params;
                }
            };

            Volley.newRequestQueue(this).add(volleyMultipartRequest);

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "File read error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private byte[] getBytes(Uri uri) throws IOException {
        InputStream iStream = getContentResolver().openInputStream(uri);
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len;
        while ((len = iStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        if(iStream != null) iStream.close();
        return byteBuffer.toByteArray();
    }

    private String getFileName(Uri uri) {
        String result = null;
        if ("content".equals(uri.getScheme())) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
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
