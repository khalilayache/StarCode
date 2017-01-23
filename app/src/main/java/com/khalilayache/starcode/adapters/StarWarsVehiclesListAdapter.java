package com.khalilayache.starcode.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.khalilayache.starcode.R;
import com.khalilayache.starcode.models.StarWarsVehicle;

import java.util.List;

/**
 * @author USUARIO
 * @since 23/01/2017.
 */

public class StarWarsVehiclesListAdapter extends ArrayAdapter<StarWarsVehicle> {

    public StarWarsVehiclesListAdapter(Context context, List<StarWarsVehicle> starWarsVehicles) {
        super(context, 0, starWarsVehicles);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listViewItem = convertView;

        if(listViewItem == null){
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.list_pager_2_itens,parent,false);
        }

        final StarWarsVehicle currentVehicle = getItem(position);

        if(currentVehicle != null) {
            TextView nameView = (TextView) listViewItem.findViewById(R.id.name);
            nameView.setText(currentVehicle.getName());

            TextView modelView = (TextView) listViewItem.findViewById(R.id.model);
            modelView.setText(currentVehicle.getModel());
        }

        return listViewItem;
    }

}
