package com.example.finalproject.Yahya;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;

import java.util.ArrayList;
import java.util.List;

public class ToDoListActivity extends AppCompatActivity {

    private EditText edtTask;
    private Button btnAdd;
    private ListView taskListView;
    private ArrayAdapter<String> adapter;
    private List<String> taskNames = new ArrayList<>();
    private List<ToDoRecord> taskList = new ArrayList<>();


    private ToDoDatabase db;
    private ToDoDao taskDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        edtTask = findViewById(R.id.edtTask);
        btnAdd = findViewById(R.id.btnAdd);
        taskListView = findViewById(R.id.taskListView);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskNames);
        taskListView.setAdapter(adapter);
        taskListView.setOnItemLongClickListener((parent, view, position, id) -> {
            ToDoRecord selectedTask = taskList.get(position);
            new Thread(() -> {
                taskDao.delete(selectedTask);
                runOnUiThread(() -> {
                    taskNames.remove(position);
                    taskList.remove(position);
                    adapter.notifyDataSetChanged();
                });
            }).start();
            return true;
        });


        db = ToDoDatabase.getInstance(getApplicationContext());
        taskDao = db.studyTaskDao();

        loadTasks();

        btnAdd.setOnClickListener(v -> {
            String taskText = edtTask.getText().toString().trim();
            if (!taskText.isEmpty()) {
                new Thread(() -> {
                    taskDao.insert(new ToDoRecord(taskText, false));
                    runOnUiThread(() -> {
                        edtTask.setText("");
                        loadTasks();
                    });
                }).start();
            }
        });
    }

    private void loadTasks() {
        new Thread(() -> {
            List<ToDoRecord> tasks = taskDao.getAllTasks();
            if (tasks == null) return; // Just in case

            taskNames.clear();
            taskList.clear();
            taskList.addAll(tasks);
            for (ToDoRecord task : tasks) {
                taskNames.add(task.title);
            }
            runOnUiThread(() -> adapter.notifyDataSetChanged());
        }).start();
    }

}
