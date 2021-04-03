package com.example.androidfinalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class ShowProductActivity extends AppCompatActivity {
    ImageView mainImage;
    TextView productName;
    TextView productCategory;
    TextView productDescription;
    Button addCartButton;
    Spinner addCategory;
    private static final int PICK_IMAGE = 1;
    Uri imageUri;
    Cart currentItem = new Cart();
    EditText nameInput;
    EditText descInput;





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
//        addCartButton.setOnClickListener();
//        addCartButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                saveData();
//            }
//        });

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


    public void saveCart(View v){
        //Connection to server
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myProducts = database.getReference("Cart"); //Cart DataBase reference
        DatabaseReference newChildRef = myProducts.push();
        String key = newChildRef.getKey();


        // Get the data from an ImageView as bytes
        mainImage.setDrawingCacheEnabled(true);
        mainImage.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) mainImage.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        String imageDataEncoded = Base64.encodeToString(data, Base64.DEFAULT);
        currentItem.setImageDataEncoded(imageDataEncoded);



//        mainImage = (ImageView) findViewById(R.id.show_product_image);
        productCategory = (TextView) findViewById(R.id.show_product_category);

        //Creating Current Product information @Product_Creation
//        currentItem.setImageDataEncoded(mainImage.toString());
        currentItem.setName(productName.getText().toString());
        currentItem.setDescription(productDescription.getText().toString());
        currentItem.setCategory(productCategory.getText().toString());
        currentItem.setId(key);

        //Sending Current Product to server
        myProducts.child(key).setValue(currentItem)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Added to Cart", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ShowProductActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Failed to add to cart", Toast.LENGTH_LONG).show();
                    }
                });


    }

}