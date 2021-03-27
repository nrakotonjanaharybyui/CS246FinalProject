package com.example.androidfinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class SaveCartActivity extends AppCompatActivity {
    ListView cartList;

    //Database implementation
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myCartRef = database.getReference("Cart");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_cart);
        loadProducts();
    }
    public void loadProducts(){
        cartList = (ListView) findViewById(R.id.cart_list);

        //Query to database
        Query queryProducts = myCartRef.orderByKey();
                ArrayList<Cart> toDisplayList = new ArrayList<>();
                CustomAdapter2 currentAdapter = new CustomAdapter2(SaveCartActivity.this, 0, toDisplayList);
                cartList.setAdapter(currentAdapter);


        };

//       queryProducts.addValueEventListener(queryProductsListener);
//        cartList.setOnItemClickListener(this);
   
}