package com.example.androidfinalproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddActivity extends AppCompatActivity {
    Spinner addCategory;
    ImageView productImage;
    private static final int PICK_IMAGE = 1;
    Uri imageUri;
    Product currentProduct = new Product();
    EditText nameInput;
    EditText descInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //Handling views
        productImage = (ImageView) findViewById(R.id.add_product_image);
        nameInput = (EditText) findViewById(R.id.add_name);
        descInput = (EditText) findViewById(R.id.add_description);

        //Handling view header
        getSupportActionBar().setTitle("Add a New Product");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Handling spinner view
        addCategory = (Spinner) findViewById(R.id.add_category);
        ArrayAdapter<CharSequence> addCategoryAdapter = ArrayAdapter.createFromResource(this, R.array.category,
                android.R.layout.simple_spinner_item);
        addCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addCategory.setAdapter(addCategoryAdapter);

    }

    //Triggered when user clicks the "add an image" button to update the product image view
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public void addPicture(View v){
        Toast.makeText(this, "Browsing device for image", Toast.LENGTH_LONG).show();
        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(gallery,
                "Select Picture"),
                PICK_IMAGE);

    }

    //Image from gallery handling 2
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            imageUri = data.getData();

            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                productImage.setImageBitmap(bitmap);

                //Serializing the image (String) because Firebase Realtime Datastorage is JSON based
                //Setting Current Product image @Product_Creation
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageData = baos.toByteArray();
                String imageDataEncoded = Base64.encodeToString(imageData, Base64.DEFAULT);
                currentProduct.setImageDataEncoded(imageDataEncoded);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    //Triggered when everything is set and the user sends all data to server
    public void saveProduct(View v){
        //Connection to server
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myProducts = database.getReference("Products");
        DatabaseReference newChildRef = myProducts.push();
        String key = newChildRef.getKey();

        //Creating Current Product information @Product_Creation
        currentProduct.setName(nameInput.getText().toString());
        currentProduct.setDescription(descInput.getText().toString());
        currentProduct.setCategory(addCategory.getSelectedItem().toString());
        currentProduct.setId(key);

        //Sending Current Product to server
        myProducts.child(key).setValue(currentProduct)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Snackbar.make(findViewById(android.R.id.content), "Product Uploaded", Snackbar.LENGTH_LONG).show();
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed to upload Product", Toast.LENGTH_LONG).show();
            }
        });


    }
}