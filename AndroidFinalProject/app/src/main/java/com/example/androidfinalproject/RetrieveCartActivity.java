package com.example.androidfinalproject;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RetrieveCartActivity extends AppCompatActivity {
    ListView myListView;
    List<Cart> cartList;
    DatabaseReference cartDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_cart);

        //Handling view header
        getSupportActionBar().setTitle("Your cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myListView = findViewById(R.id.myListView);
        cartList = new ArrayList<>();
        cartDbRef = FirebaseDatabase.getInstance().getReference("Cart");
        cartDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cartList.clear();

                for (DataSnapshot cartDataSnap : snapshot.getChildren()){
                    Cart cart = cartDataSnap.getValue(Cart.class);
                    cartList.add(cart);
                }
                ListAdaptor adaptor = new ListAdaptor(RetrieveCartActivity.this,cartList);
                myListView.setAdapter(adaptor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}