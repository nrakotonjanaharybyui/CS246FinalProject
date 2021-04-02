package com.example.androidfinalproject;

import android.app.Activity;
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

public class ListAdaptor extends ArrayAdapter {
    private Activity mContext;
    List<Cart> cartList;
    public ListAdaptor(Activity mContext, List<Cart> cartList){
        super(mContext, R.layout.list_item,cartList);
        this.mContext = mContext;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.list_item,null,true);
        TextView name = listItemView.findViewById(R.id.c_product_name);
        TextView description = listItemView.findViewById(R.id.c_product_description);
        TextView category = listItemView.findViewById(R.id.c_product_category);
        ImageView image = listItemView.findViewById(R.id.c_product_image);


        Cart cart = cartList.get(position);

        //Decoding product image
//        String imageDataEncoded = cart.getImageDataEncoded();
//        if (imageDataEncoded!=""){
//            byte[] encodeByte = Base64.decode(imageDataEncoded, Base64.DEFAULT);
//            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
//            image.setImageBitmap(bitmap);
//        }

        name.setText(cart.getName());
        description.setText(cart.getDescription());
        category.setText(cart.getCategory());

        return listItemView;
    }
}
