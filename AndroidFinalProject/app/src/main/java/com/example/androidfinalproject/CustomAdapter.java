package com.example.androidfinalproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Product> {
    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<Product> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Product product = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list, parent, false);
        }
        TextView itemName = (TextView) convertView.findViewById(R.id.list_product_name);
        TextView itemDescription = (TextView) convertView.findViewById(R.id.list_product_description);
        TextView itemCategory = (TextView) convertView.findViewById(R.id.list_product_category);
        ImageView itemImage = (ImageView) convertView.findViewById(R.id.list_product_image);

        //Decoding product image
        String imageDataEncoded = product.getImageDataEncoded();
        byte[] encodeByte = Base64.decode(imageDataEncoded, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

        if(product.getCategory().equals("Choose a category")){
            product.setCategory("Not categorized");
        }

        itemName.setText(product.getName());
        itemDescription.setText(product.getDescription());
        itemImage.setImageBitmap(bitmap);
        itemCategory.setText(product.getCategory());

        String categoryString = product.getCategory();

        /*if (categoryString.equals("Choose a category")){
            itemCategory.setText("Not categorized");
        }
        else{
            itemCategory.setText(product.getCategory());
        }*/


        //return super.getView(position, convertView, parent);
        return convertView;
    }
}
