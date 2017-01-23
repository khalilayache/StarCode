package com.khalilayache.starcode.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.khalilayache.starcode.R;
import com.khalilayache.starcode.models.StarWarsFilm;
import com.khalilayache.starcode.models.StarWarsShip;

import java.util.List;

/**
 * @author USUARIO
 * @since 23/01/2017.
 */

public class StarWarsShipsListAdapter extends ArrayAdapter<StarWarsShip> {

    public StarWarsShipsListAdapter(Context context, List<StarWarsShip> starWarsShips) {
        super(context, 0, starWarsShips);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listViewItem = convertView;

        if(listViewItem == null){
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.list_pager_2_itens,parent,false);
        }

        final StarWarsShip currentShip = getItem(position);

        if(currentShip != null) {
            TextView nameView = (TextView) listViewItem.findViewById(R.id.name);
            nameView.setText(currentShip.getName());

            TextView modelView = (TextView) listViewItem.findViewById(R.id.model);
            modelView.setText(currentShip.getModel());


        }

        return listViewItem;
    }

}
