package com.example.category;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TaskActivity extends AppCompatActivity {
    private ListView listView;
    private TaskAdapter taskAdapter;
    private List<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        listView = findViewById(R.id.list_tasks);
        EditText taskNameEditText = findViewById(R.id.edit_task_name);
        EditText taskDescriptionEditText = findViewById(R.id.edit_task_description);
        Button addButton = findViewById(R.id.button_add_task);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        tasks = new ArrayList<>();

        Category category = getIntent().getParcelableExtra("category");
        getSupportActionBar().setTitle(category.getName());

        taskAdapter = new TaskAdapter(this, tasks);
        listView.setAdapter(taskAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = taskNameEditText.getText().toString();
                String taskDescription = taskDescriptionEditText.getText().toString();

                // Сохранение задачи в базе данных
                long taskId = databaseHelper.insertTask(taskName, taskDescription);

                // Очистка полей ввода
                taskNameEditText.setText("");
                taskDescriptionEditText.setText("");

                if (!taskName.isEmpty() && !taskDescription.isEmpty()) {
                    Task task = new Task(taskName, taskDescription);
                    tasks.add(task);
                    taskAdapter.notifyDataSetChanged();

                    taskNameEditText.setText("");
                    taskDescriptionEditText.setText("");
                } else {
                    Toast.makeText(TaskActivity.this, "Введите название и описание задачи", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}