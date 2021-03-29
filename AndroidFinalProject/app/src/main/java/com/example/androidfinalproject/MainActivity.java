package com.example.androidfinalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
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


/**
 * MainActivity
 * App main screen
 * Displays new feeds from database
 * Allows user to add a new item, search items
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    Button addButton;
    ImageButton searchButton;
    ListView productList;

    //Database implementation
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myProductsRef = database.getReference("Products");

    View loaderBackground;
    ProgressBar loaderIcon;
    TextView loaderHeading, loaderText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Hiding default header
        getSupportActionBar().hide();

        //loading products from online database
        loadProducts();
    }

    //Starts when the user clicks the search icon
    public void startSearchActivity(View v){
        Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(searchIntent);

    }

    /**
     * addProduct
     * Starts the AddActivity class where new items are created and sent to database
     * @param v
     */
    public void addProduct(View v){
        Intent addProductIntent = new Intent(MainActivity.this, AddActivity.class);
        startActivity(addProductIntent);
    }


    /**
     * loadProducts
     * Loads the items previously created and saved on the database
     */
    public void loadProducts(){
        productList = (ListView) findViewById(R.id.main_list);

        //Query to database
        Query queryProducts = myProductsRef.orderByKey();

        ValueEventListener queryProductsListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Product> toDisplayList = new ArrayList<>();
                Iterable<DataSnapshot> snapshotIterable = snapshot.getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterable.iterator();

                while(iterator.hasNext()){
                    DataSnapshot next = (DataSnapshot) iterator.next();
                    String name = next.child("name").getValue().toString();
                    String description = next.child("description").getValue().toString();
                    String id = next.child("id").getValue().toString();
                    String category = next.child("category").getValue().toString();
                    String imageDataEncoded = next.child("imageDataEncoded").getValue().toString();

                    Product currentProduct = new Product(name, description, id, category, imageDataEncoded);

                    toDisplayList.add(0, currentProduct);
                }

                CustomAdapter currentAdapter = new CustomAdapter(MainActivity.this, 0, toDisplayList);
                productList.setAdapter(currentAdapter);

                hideLoaderScreen();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "No data available", Toast.LENGTH_LONG).show();

            }
        };

        queryProducts.addValueEventListener(queryProductsListener);
        productList.setOnItemClickListener(this);
    }

    /**
     * onItemClick
     * Triggered when the user clicks an item from the ListView
     * Starts a new intent (ShowProductActivity) that displays the details of the item
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Product currentProduct = (Product) parent.getItemAtPosition(position);
        String nameC = currentProduct.getName();
        String idC = currentProduct.getId();
        String categoryC = currentProduct.getCategory();
        String descriptionC = currentProduct.getDescription();
        String imageDataEncodedC = currentProduct.getImageDataEncoded();

        //Creating the intent and passing the hash map through it to show activity
        Intent showIntent = new Intent(this, ShowProductActivity.class);
        showIntent.putExtra("name", nameC);
        showIntent.putExtra("category", categoryC);
        showIntent.putExtra("description", descriptionC);
        showIntent.putExtra("id", idC);

        startActivity(showIntent);
    }

    /**
     * hideLoaderScreen
     * Hides the loader elements when all items from database are ready to display
     * on the ListView
     */
    public void hideLoaderScreen(){
        loaderBackground = (View) findViewById(R.id.progressBackground);
        loaderIcon = (ProgressBar) findViewById(R.id.progressIcon);
        loaderHeading = (TextView) findViewById(R.id.progressHeading);
        loaderText = (TextView) findViewById(R.id.progressText);
        addButton = (Button) findViewById(R.id.button);

        loaderBackground.setVisibility(View.GONE);
        loaderIcon.setVisibility(View.GONE);
        loaderText.setVisibility(View.GONE);
        loaderHeading.setVisibility(View.GONE);

        addButton.setVisibility(View.VISIBLE);

    }
    public void startCartActivity(View v){
        Intent cartIntent = new Intent(MainActivity.this, SaveCartActivity.class);
        startActivity(cartIntent);

    }
}
