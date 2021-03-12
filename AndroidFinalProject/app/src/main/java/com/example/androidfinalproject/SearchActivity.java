package com.example.androidfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    EditText searchText;
    ListView searchResultListView;
    Spinner searchCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().setTitle("Search");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchCategory = (Spinner) findViewById(R.id.search_page_category_spinner);
        ArrayAdapter<CharSequence> searchCategoryAdapter = ArrayAdapter.createFromResource(this, R.array.category,
                android.R.layout.simple_spinner_item);
        searchCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchCategory.setAdapter(searchCategoryAdapter);

    }

    public void searchThis(View v){
        searchText = (EditText) findViewById(R.id.search_page_searchText);
        searchResultListView = (ListView) findViewById(R.id.search_page_search_result);
        searchCategory = (Spinner) findViewById(R.id.search_page_category_spinner);
        String searchKey = searchText.getText().toString();
        String searchCategoryKey = searchCategory.getSelectedItem().toString();

        searchResultListView.setAdapter(null);

        if (searchKey.length() == 0){
            Toast.makeText(this, "Please enter a keyword", Toast.LENGTH_LONG).show();
        }
        else{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myProductsRef = database.getReference("Products");
            Query queryProducts = myProductsRef.orderByKey();

            ValueEventListener queryPoductsListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<SearchResultItem> searchToDisplayList = new ArrayList<>();
                    Iterable<DataSnapshot> snapshotIterable = snapshot.getChildren();
                    Iterator<DataSnapshot> iterator = snapshotIterable.iterator();

                    while(iterator.hasNext()){
                        DataSnapshot next = (DataSnapshot) iterator.next();

                        if(!searchCategoryKey.equals("Choose a category")){
                            if(next.child("category").getValue().toString().equals(searchCategoryKey)){
                                if(next.child("name").getValue().toString().contains(searchKey) || next.child("description").getValue().toString().contains(searchKey)){
                                    String name = next.child("name").getValue().toString();
                                    String description = next.child("description").getValue().toString();
                                    String id = next.child("id").getValue().toString();
                                    String category = next.child("category").getValue().toString();

                                    SearchResultItem currentItem = new SearchResultItem(name, description, id, category);
                                    searchToDisplayList.add(0, currentItem);
                                }
                            }
                        }
                        else{
                            if(next.child("name").getValue().toString().contains(searchKey) || next.child("description").getValue().toString().contains(searchKey)){
                                String name = next.child("name").getValue().toString();
                                String description = next.child("description").getValue().toString();
                                String id = next.child("id").getValue().toString();
                                String category = next.child("category").getValue().toString();

                                SearchResultItem currentItem = new SearchResultItem(name, description, id, category);
                                searchToDisplayList.add(0, currentItem);
                            }
                        }

                    }//End of while loop

                    if(searchToDisplayList.size() == 0){
                        Toast.makeText(SearchActivity.this, "No match found", Toast.LENGTH_LONG).show();
                    }
                    else{
                        CustomSearchAdapter currentAdapter = new CustomSearchAdapter(SearchActivity.this, 0, searchToDisplayList);
                        searchResultListView.setAdapter(currentAdapter);
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };//End of Value listener detail

            queryProducts.addValueEventListener(queryPoductsListener);
            searchResultListView.setOnItemClickListener(this);
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SearchResultItem currentItem = (SearchResultItem) parent.getItemAtPosition(position);
        String idC = currentItem.getId();

        //Creating the intent and passing the hash map through it to show activity
        Intent showIntent = new Intent(this, ShowProductActivity.class);
        showIntent.putExtra("id", idC);

        startActivity(showIntent);
    }
}