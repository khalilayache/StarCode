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
import com.khalilayache.starcode.adapters.StarWarsVehiclesListAdapter;
import com.khalilayache.starcode.models.StarWarsChar;
import com.khalilayache.starcode.models.StarWarsVehicle;
import com.khalilayache.starcode.utils.ApiUtils;
import com.khalilayache.starcode.utils.SQLUtils;

import java.util.ArrayList;

/**
 * @author USUARIO
 * @since 22/01/2017.
 */

public class VehicleFragment extends Fragment {
    private StarWarsChar starWarsChar;
    private View rootView;
    private View loadingIndicator;
    private TextView emptyStateTextView;
    private ArrayList<StarWarsVehicle> vehicleArrayList;

    private StarWarsVehicleAsyncTask starWarsVehicleTask;
    private StarWarsVehiclesListAdapter adapter;
    private ListView starWarsVehicleListView;

    public VehicleFragment() {

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


        starWarsVehicleListView = (ListView) rootView.findViewById(R.id.list);
        starWarsVehicleListView.setEmptyView(emptyStateTextView);
        emptyStateTextView.setText(R.string.no_vehicles_found);


        if(starWarsChar.getVehicles() != null) {
            if (starWarsChar.getVehicles().size() > 0) {
                emptyStateTextView.setVisibility(View.GONE);
                SQLUtils db = new SQLUtils(rootView.getContext());
                vehicleArrayList = db.getAllStarWarsVehicle(starWarsChar.getName());
                if (vehicleArrayList.size() == 0 || vehicleArrayList == null) {
                    starWarsVehicleTask = new StarWarsVehicleAsyncTask();
                    starWarsVehicleTask.execute();
                } else {
                    fillList(vehicleArrayList);
                }
            } else {
                loadingIndicator.setVisibility(View.GONE);
            }
        }else{
            loadingIndicator.setVisibility(View.GONE);
        }

        return rootView;
    }

    private void fillList(ArrayList<StarWarsVehicle> vehiclesArrayList) {

        adapter = new StarWarsVehiclesListAdapter(this.getActivity().getApplicationContext(), vehiclesArrayList );
        starWarsVehicleListView.setAdapter(adapter);
        loadingIndicator.setVisibility(View.GONE);
        emptyStateTextView.setVisibility(View.GONE);
    }

    //region Star Wars Vehicle AsyncTask
    private class StarWarsVehicleAsyncTask extends AsyncTask<Void, Void, ArrayList<StarWarsVehicle>> {

        @Override
        protected ArrayList<StarWarsVehicle> doInBackground(Void... urls) {
            if(starWarsChar.getVehicles() == null){
                return null;
            }

            return ApiUtils.fetchVehicleData(starWarsChar.getVehicles());
        }

        @Override
        protected void onPostExecute(ArrayList<StarWarsVehicle> starWarsVehicles) {
            starWarsVehicleTask = null;
            if(starWarsVehicles != null) {
                if(starWarsVehicles.size() > 0) {
                    SQLUtils db = new SQLUtils(rootView.getContext());
                    db.insertStarWarsVehicle(starWarsVehicles, starWarsChar.getName());
                    fillList(starWarsVehicles);
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
