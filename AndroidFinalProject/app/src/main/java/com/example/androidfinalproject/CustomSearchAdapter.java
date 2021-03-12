package com.example.androidfinalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomSearchAdapter extends ArrayAdapter<SearchResultItem> {
    public CustomSearchAdapter(@NonNull Context context, int resource, @NonNull List<SearchResultItem> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        SearchResultItem currentItem = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.search_result_list, parent, false);
        }
        TextView itemName = (TextView) convertView.findViewById(R.id.search_result_list_name);
        TextView itemCategory = (TextView) convertView.findViewById(R.id.search_result_list_category);
        TextView itemDescription = (TextView) convertView.findViewById(R.id.search_result_list_description);

        if(currentItem.getCategory().equals("Choose a category")){
            currentItem.setCategory("Not categorized");
        }

        itemName.setText(currentItem.getName());
        itemCategory.setText(currentItem.getCategory());
        itemDescription.setText(currentItem.getDescription());

        return convertView;
    }
}
