package com.example.finalproject.Yahya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalproject.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AssignmentSubmitionActivity extends AppCompatActivity {

    TextView titleTextView, subjectTextView, dueDateTextView;
    Button btnSubmit, btnSelectFile;
    TextView textViewSelectedFile;

    String assignmentId;
    int studentId = -1;
    private static final int PICK_FILE_REQUEST = 1;
    private Uri selectedFileUri = null;

    private static final String SUBMIT_URL = "http://10.0.2.2:80/MobileProject/submitStudentAssignment.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_submition);

        // Initialize Views
        titleTextView = findViewById(R.id.assignmentTitle);
        subjectTextView = findViewById(R.id.assignmentSubject);
        dueDateTextView = findViewById(R.id.assignmentDueDate);
        btnSelectFile = findViewById(R.id.btnSelectFile);
        textViewSelectedFile = findViewById(R.id.textViewSelectedFile);
        btnSubmit = findViewById(R.id.btnSubmitAssigment);

        // Get Intent Data
        Intent intent = getIntent();
        assignmentId = intent.getStringExtra("id");
        String title = intent.getStringExtra("title");
        String subject = intent.getStringExtra("subject");
        String dueDate = intent.getStringExtra("dueDate");
        studentId = intent.getIntExtra("student_id", -1);
        String description = getIntent().getStringExtra("description");
        String attachmentUrl = getIntent().getStringExtra("attachment_url");

        TextView descriptionView = findViewById(R.id.assignmentDescription);
        TextView attachmentView = findViewById(R.id.assignmentAttachmentUrl);

        descriptionView.setText("Description: " + description);
        attachmentView.setText("Attachment: " + attachmentUrl);
        attachmentView.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(attachmentUrl));
            startActivity(browserIntent);
        });


        if (studentId == -1) {
            Toast.makeText(this, "Missing student ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Set data
        titleTextView.setText(title);
        subjectTextView.setText(subject);
        dueDateTextView.setText("Due: " + dueDate);

        btnSelectFile.setOnClickListener(v -> {
            Intent fileIntent = new Intent(Intent.ACTION_GET_CONTENT);
            fileIntent.setType("*/*");
            fileIntent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(fileIntent, "Select File"), PICK_FILE_REQUEST);
        });

        btnSubmit.setOnClickListener(v -> submitAssignment());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedFileUri = data.getData();
            if (selectedFileUri != null) {
                String fileName = getFileName(selectedFileUri);
                textViewSelectedFile.setText(fileName);
            }
        }
    }


    private void submitAssignment() {
        if (selectedFileUri == null) {
            Toast.makeText(this, "Please select a file", Toast.LENGTH_SHORT).show();
            return;
        }

        String fileName = getFileName(selectedFileUri);

        try {
            InputStream inputStream = getContentResolver().openInputStream(selectedFileUri);
            byte[] fileBytes = getBytes(inputStream);
            String mimeType = getContentResolver().getType(selectedFileUri);

            VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(
                    Request.Method.POST,
                    SUBMIT_URL,
                    response -> {
                        String responseStr = new String(response.data);
                        Log.d("UploadResponse", responseStr);
                        try {
                            JSONObject obj = new JSONObject(responseStr);
                            boolean error = obj.getBoolean("error");
                            String message = obj.getString("message");

                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                            if (!error) {
                                finish(); // Close the activity
                            }
                        } catch (JSONException e) {
                            Toast.makeText(this, "Invalid server response", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        Toast.makeText(this, "Upload error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
            ) {
                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();
                    params.put("file", new DataPart(fileName, fileBytes, mimeType));
                    return params;
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("student_id", String.valueOf(studentId));
                    params.put("assignment_id", assignmentId);
                    return params;
                }
            };

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(multipartRequest);

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "File read error", Toast.LENGTH_SHORT).show();
        }
    }


    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }


//    private void submitAssignment() {
//        if (selectedFileUri == null) {
//            Toast.makeText(this, "Please select a file", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        final String fileName = getFileName(selectedFileUri);
//
//        RequestQueue queue = Volley.newRequestQueue(this);
//
//        StringRequest request = new StringRequest(Request.Method.POST, SUBMIT_URL,
//                response -> {
//                    Log.e("AssignmentSubmit", "Response: " + response);
//                    try {
//                        JSONObject jsonObject = new JSONObject(response);
//                        String msg = jsonObject.optString("message", "No message");
//                        boolean error = jsonObject.optBoolean("error", true);
//
//                        Toast.makeText(AssignmentSubmitionActivity.this, msg, Toast.LENGTH_SHORT).show();
//
//                        if (!error) {
//                            finish(); // Close activity on success
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        Toast.makeText(AssignmentSubmitionActivity.this, "Invalid response from server", Toast.LENGTH_SHORT).show();
//                    }
//                },
//                error -> Toast.makeText(AssignmentSubmitionActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show()
//        ) {
//            @Override
//            public String getBodyContentType() {
//                return "application/x-www-form-urlencoded; charset=UTF-8";
//            }
//
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                params.put("student_id", String.valueOf(studentId));
//                params.put("assignment_id", assignmentId);
//                params.put("submission_filename", fileName);
//                return params;
//            }
//        };
//
//        queue.add(request);
//    }

    private String getFileName(Uri uri) {
        String result = null;
        if ("content".equals(uri.getScheme())) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    result = cursor.getString(nameIndex);
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }
}
