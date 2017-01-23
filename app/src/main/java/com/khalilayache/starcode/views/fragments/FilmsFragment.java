package com.khalilayache.starcode.views.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.khalilayache.starcode.R;
import com.khalilayache.starcode.adapters.StarWarsFilmsListAdapter;
import com.khalilayache.starcode.models.StarWarsChar;
import com.khalilayache.starcode.models.StarWarsFilm;
import com.khalilayache.starcode.utils.ApiUtils;
import com.khalilayache.starcode.utils.SQLUtils;

import java.util.ArrayList;


/**
 * @author USUARIO
 * @since 22/01/2017.
 */

public class FilmsFragment extends Fragment {

    private StarWarsChar starWarsChar;
    private View rootView;
    private View loadingIndicator;
    private TextView emptyStateTextView;
    private ArrayList<StarWarsFilm> filmArrayList;

    private StarWarsFilmsAsyncTask starWarsFilmsTask;
    private StarWarsFilmsListAdapter adapter;
    private ListView starWarsFilmsListView;

    public FilmsFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        starWarsChar = (StarWarsChar) bundle.getSerializable("char");

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView  = inflater.inflate(R.layout.pager_list,container,false);

        loadingIndicator = rootView.findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.VISIBLE);
        emptyStateTextView = (TextView) rootView.findViewById(R.id.empty_view);


        starWarsFilmsListView = (ListView) rootView.findViewById(R.id.list);
        starWarsFilmsListView.setEmptyView(emptyStateTextView);
        emptyStateTextView.setText(R.string.no_films_found);


        if(starWarsChar.getFilms() != null) {
            if (starWarsChar.getFilms().size() > 0) {
                emptyStateTextView.setVisibility(View.GONE);
                SQLUtils db = new SQLUtils(rootView.getContext());
                filmArrayList = db.getAllStarWarsFilms(starWarsChar.getName());
                if (filmArrayList.size() == 0 || filmArrayList == null){
                    starWarsFilmsTask = new StarWarsFilmsAsyncTask();
                    starWarsFilmsTask.execute();
                }else{
                    fillList(filmArrayList);
                }
            } else {
                loadingIndicator.setVisibility(View.GONE);
            }
        }else{
            loadingIndicator.setVisibility(View.GONE);
        }


        return rootView;
    }

    private void fillList(ArrayList<StarWarsFilm> filmArrayList) {

        adapter = new StarWarsFilmsListAdapter(this.getActivity().getApplicationContext(), filmArrayList );
        starWarsFilmsListView.setAdapter(adapter);
        loadingIndicator.setVisibility(View.GONE);
        emptyStateTextView.setVisibility(View.GONE);
    }

    //region Star Wars Films AsyncTask
    private class StarWarsFilmsAsyncTask extends AsyncTask<Void, Void, ArrayList<StarWarsFilm>> {

        @Override
        protected ArrayList<StarWarsFilm> doInBackground(Void... urls) {
            if(starWarsChar.getFilms() == null){
                return null;
            }

            return ApiUtils.fetchFilmsData(starWarsChar.getFilms());
        }

        @Override
        protected void onPostExecute(ArrayList<StarWarsFilm> starWarsFilms) {
            starWarsFilmsTask = null;
            if(starWarsFilms != null) {
                if(starWarsFilms.size() > 0) {
                    SQLUtils db = new SQLUtils(rootView.getContext());
                    db.insertStarWarsFilms(starWarsFilms, starWarsChar.getName());
                    fillList(starWarsFilms);
                }else{
                    loadingIndicator.setVisibility(View.GONE);
                    emptyStateTextView.setVisibility(View.VISIBLE);
                }
            }else{
                loadingIndicator.setVisibility(View.GONE);
                emptyStateTextView.setVisibility(View.VISIBLE);
            }
        }
    }
    //endregion
}
