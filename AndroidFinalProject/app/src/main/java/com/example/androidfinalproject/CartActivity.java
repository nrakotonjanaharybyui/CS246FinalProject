package com.example.androidfinalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button NextProcessBtn;
    private TextView txtTotalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        NextProcessBtn = (Button) findViewById(R.id.next_process_btn);
        txtTotalAmount = (TextView) findViewById(R.id.total_price);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

//        FirebaseRecyclerOptions<Product> options = new FirebaseRecyclerOptions.Builder<Product>()
//                .setQuery(cartListRef.child("User View").child())
        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Product");
        FirebaseRecyclerOptions<Product> options = new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(cartListRef.child("id"),Product.class).build();

    }
}