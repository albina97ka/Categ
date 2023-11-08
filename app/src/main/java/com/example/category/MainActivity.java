package com.example.category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ThemedSpinnerAdapter;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.content.SharedPreferences;
import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DBHelper dbHelper;
    private ListView listView;
    private CategoryAdapter categoryAdapter;
    private List<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        listView = findViewById(R.id.list_categories);
        Button addCategoryButton = findViewById(R.id.add_category_button);
        EditText categoryNameEditText = findViewById(R.id.edit_category_name);

        categories = new ArrayList<>();
        categories.add(new Category("Категория", 0));

        categoryAdapter = new CategoryAdapter(this, categories);
        listView.setAdapter(categoryAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category selectedCategory = categories.get(position);
                Intent intent = new Intent(MainActivity.this, TaskActivity.class);
                intent.putExtra("category", selectedCategory);
                startActivity(intent);
            }
            });
            addCategoryButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick (View view){
                String categoryName = categoryNameEditText.getText().toString();
                if (!categoryName.isEmpty()) {
                    categories.add(new Category(categoryName, 0));
                    categoryAdapter.notifyDataSetChanged();
                    categoryNameEditText.setText("");
                }
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    long taskId = getTaskIdFromSomewhere();
                    editor.putLong("taskId", taskId);
                    editor.apply();
            }
        });
    }
    private long getTaskIdFromSomewhere() {
        long taskId = 0;
        return taskId;
    }
}
