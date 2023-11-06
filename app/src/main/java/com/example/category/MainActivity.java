package com.example.category;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private CategoryAdapter categoryAdapter;
    private List<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_categories);

        categories = new ArrayList<>();
        categories.add(new Category("Категория 1", 3));
        categories.add(new Category("Категория 2", 5));
        categories.add(new Category("Категория 3", 2));

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
    }
}
