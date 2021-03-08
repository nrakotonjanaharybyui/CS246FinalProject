package com.example.androidfinalproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ShowProductActivity extends AppCompatActivity {
    ImageView mainImage;
    TextView productName;
    TextView productCategory;
    TextView productDescription;
    Button addCartButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);

        //Database implementation
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myProductsRef = database.getReference("Products");

        //Handling view header
        getSupportActionBar().setTitle("Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mainImage = (ImageView) findViewById(R.id.show_product_image);
        productName = (TextView) findViewById(R.id.show_product_name);
        productCategory = (TextView) findViewById(R.id.show_product_category);
        productDescription = (TextView) findViewById(R.id.show_product_description);
        addCartButton = (Button) findViewById(R.id.show_product_add_to_cart_button);

        Intent showIntent = getIntent();

        //Populating the views
        String name = showIntent.getStringExtra("name");
        String category = showIntent.getStringExtra("category");
        String description = showIntent.getStringExtra("description");
        String id = showIntent.getStringExtra("id");

        //Server request for image
        Query queryProducts = myProductsRef.orderByKey();
        DatabaseReference itemRef = myProductsRef.child(id).child("imageDataEncoded");
        itemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String imageData = snapshot.getValue().toString();
                byte[] encodeByte = Base64.decode(imageData, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                mainImage.setImageBitmap(bitmap);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        productName.setText(name);
        productCategory.setText(category);
        productDescription.setText(description);

    }
}