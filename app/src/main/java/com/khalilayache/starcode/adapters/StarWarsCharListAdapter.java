package com.khalilayache.starcode.adapters;

import  android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.khalilayache.starcode.R;
import com.khalilayache.starcode.models.StarWarsChar;
import com.khalilayache.starcode.views.DetailsActivity;

import java.util.List;

/**
 * @author USUARIO
 * @since 21/01/2017.
 */

public class StarWarsCharListAdapter extends ArrayAdapter<StarWarsChar> {


    public StarWarsCharListAdapter(Context context, List<StarWarsChar> starWarsCharList) {


        super(context, 0, starWarsCharList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listViewItem = convertView;

        if(listViewItem == null){
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.list_main_char,parent,false);
        }

        final StarWarsChar currentChar = getItem(position);

        if(currentChar != null) {
            TextView nameView = (TextView) listViewItem.findViewById(R.id.name);
            nameView.setText(currentChar.getName());

            TextView urlView = (TextView) listViewItem.findViewById(R.id.url);
            urlView.setText(currentChar.getUrl());

            TextView dateView = (TextView) listViewItem.findViewById(R.id.date);
            dateView.setText(currentChar.getDate());

            TextView timeView = (TextView) listViewItem.findViewById(R.id.time);
            timeView.setText(currentChar.getTime());


            listViewItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DetailsActivity.class);
                    intent.putExtra("char",currentChar);
                    getContext().startActivity(intent);
                }
            });
        }

        return listViewItem;
    }
}
