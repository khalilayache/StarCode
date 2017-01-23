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
import com.khalilayache.starcode.adapters.StarWarsShipsListAdapter;
import com.khalilayache.starcode.models.StarWarsChar;
import com.khalilayache.starcode.models.StarWarsShip;
import com.khalilayache.starcode.utils.ApiUtils;
import com.khalilayache.starcode.utils.SQLUtils;

import java.util.ArrayList;


/**
 * @author USUARIO
 * @since 22/01/2017.
 */

public class ShipsFragment extends Fragment {

    private StarWarsChar starWarsChar;
    private View rootView;
    private View loadingIndicator;
    private TextView emptyStateTextView;
    private ArrayList<StarWarsShip> shipArrayList;

    private StarWarsShipsAsyncTask starWarsShipTask;
    private StarWarsShipsListAdapter adapter;
    private ListView starWarsShipsListView;

    public ShipsFragment() {

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


        starWarsShipsListView = (ListView) rootView.findViewById(R.id.list);
        starWarsShipsListView.setEmptyView(emptyStateTextView);
        emptyStateTextView.setText(R.string.no_ships_found);


        if(starWarsChar.getStarships() != null) {
            if (starWarsChar.getStarships().size() > 0) {
                emptyStateTextView.setVisibility(View.GONE);
                SQLUtils db = new SQLUtils(rootView.getContext());
                shipArrayList = db.getAllStarWarsShips(starWarsChar.getName());
                if (shipArrayList.size() == 0 || shipArrayList == null) {
                    starWarsShipTask = new StarWarsShipsAsyncTask();
                    starWarsShipTask.execute();
                } else {
                    fillList(shipArrayList);
                }
            } else {
                loadingIndicator.setVisibility(View.GONE);
            }
        }else{
            loadingIndicator.setVisibility(View.GONE);
        }

        return rootView;
    }

    private void fillList(ArrayList<StarWarsShip> shipArrayList) {
        try {
            adapter = new StarWarsShipsListAdapter(this.getActivity().getApplicationContext(), shipArrayList);
            starWarsShipsListView.setAdapter(adapter);
            loadingIndicator.setVisibility(View.GONE);
            emptyStateTextView.setVisibility(View.GONE);
        }catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    //region Star Wars Ships AsyncTask
    private class StarWarsShipsAsyncTask extends AsyncTask<Void, Void, ArrayList<StarWarsShip>> {

        @Override
        protected ArrayList<StarWarsShip> doInBackground(Void... urls) {
            if(starWarsChar.getFilms() == null){
                return null;
            }

            return ApiUtils.fetchShipsData(starWarsChar.getStarships());
        }

        @Override
        protected void onPostExecute(ArrayList<StarWarsShip> starWarsShips) {
            starWarsShipTask = null;
            if(starWarsShips != null) {
                if(starWarsShips.size() > 0) {
                    SQLUtils db = new SQLUtils(rootView.getContext());
                    db.insertStarWarsShips(starWarsShips, starWarsChar.getName());
                    fillList(starWarsShips);
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
