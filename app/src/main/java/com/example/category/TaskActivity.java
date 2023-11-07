package com.example.category;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class TaskActivity extends AppCompatActivity {
    DBHelper dbHelper;
    private ListView listView;
    private TaskAdapter taskAdapter;
    private List<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        dbHelper = new DBHelper(this);

        listView = findViewById(R.id.list_tasks);
        EditText taskNameEditText = findViewById(R.id.edit_task_name);
        EditText taskDescriptionEditText = findViewById(R.id.edit_task_description);
        Button addButton = findViewById(R.id.button_add_task);

        tasks = new ArrayList<>();

        Category category = getIntent().getParcelableExtra("category");
        getSupportActionBar().setTitle(category.getName());

        taskAdapter = new TaskAdapter(this, tasks);
        listView.setAdapter(taskAdapter);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        long taskId = sharedPreferences.getLong("taskId", -1);
        if (taskId != -1) {
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = taskNameEditText.getText().toString();
                String taskDescription = taskDescriptionEditText.getText().toString();

                if (!taskName.isEmpty() && !taskDescription.isEmpty()) {
                    Task task = new Task(taskName, taskDescription);
                    tasks.add(task);
                    taskAdapter.notifyDataSetChanged();

                    taskNameEditText.setText("");
                    taskDescriptionEditText.setText("");

                    DBHelper dbHelper = new DBHelper(TaskActivity.this);
                    long taskId = dbHelper.insertTask(taskName, taskDescription, category.getId());
                    dbHelper.close();
                } else {
                    Toast.makeText(TaskActivity.this, "Введите название и описание задачи", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}