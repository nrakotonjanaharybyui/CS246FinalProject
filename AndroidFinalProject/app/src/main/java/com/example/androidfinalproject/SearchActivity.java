package com.example.androidfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class SearchActivity extends AppCompatActivity {
    EditText searchText;
    ListView searchResultListView;
    Spinner searchCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().setTitle("Search");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchCategory = (Spinner) findViewById(R.id.category_spinner);
        ArrayAdapter<CharSequence> searchCategoryAdapter = ArrayAdapter.createFromResource(this, R.array.category,
                android.R.layout.simple_spinner_item);
        searchCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchCategory.setAdapter(searchCategoryAdapter);

    }

    public void searchThis(View v){
        searchText = (EditText) findViewById(R.id.search_page_searchText);
        searchResultListView = (ListView) findViewById(R.id.search_page_search_result);
        String message = "Searching for " + searchText.getText().toString();

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

    }
}