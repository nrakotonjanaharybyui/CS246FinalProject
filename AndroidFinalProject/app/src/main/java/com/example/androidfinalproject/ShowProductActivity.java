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

        //yielding id
        String id = showIntent.getStringExtra("id");

        //Server request for object
        Query queryProducts = myProductsRef.orderByKey();
        DatabaseReference itemRef = myProductsRef.child(id);
        itemRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("name").getValue().toString();
                String category = snapshot.child("category").getValue().toString();
                String description = snapshot.child("description").getValue().toString();
                String imageData = snapshot.child("imageDataEncoded").getValue().toString();
                byte[] encodeByte = Base64.decode(imageData, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

                mainImage.setImageBitmap(bitmap);
                productName.setText(name);
                productCategory.setText(category);
                productDescription.setText(description);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}