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

import java.util.List;

/**
 * @author USUARIO
 * @since 23/01/2017.
 */
public class StarWarsFilmsListAdapter extends ArrayAdapter<StarWarsFilm> {

    public StarWarsFilmsListAdapter(Context context, List<StarWarsFilm> starWarsFilms) {
        super(context, 0, starWarsFilms);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listViewItem = convertView;

        if(listViewItem == null){
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.list_pager_4_itens,parent,false);
        }

        final StarWarsFilm currentFilm = getItem(position);

        if(currentFilm != null) {
            TextView nameView = (TextView) listViewItem.findViewById(R.id.film_name);
            nameView.setText("Star Wars: Episode " +currentFilm.getEpisode_id() + " - " + currentFilm.getTitle());

            TextView directorView = (TextView) listViewItem.findViewById(R.id.film_director);
            directorView.setText(currentFilm.getDirector());

            TextView producerView = (TextView) listViewItem.findViewById(R.id.film_producer);
            producerView.setText(currentFilm.getProducer());

            TextView releaseView = (TextView) listViewItem.findViewById(R.id.film_release_date);
            releaseView.setText(currentFilm.getRelease_date());

        }

        return listViewItem;
    }



   }
