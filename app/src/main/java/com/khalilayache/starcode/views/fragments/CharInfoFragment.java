package com.khalilayache.starcode.views.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.khalilayache.starcode.R;
import com.khalilayache.starcode.models.StarWarsChar;
import com.khalilayache.starcode.models.StarWarsPlanet;
import com.khalilayache.starcode.models.StarWarsSpecie;
import com.khalilayache.starcode.utils.ApiUtils;
import com.khalilayache.starcode.utils.SQLUtils;
import com.khalilayache.starcode.views.MainActivity;


/**
 * @author USUARIO
 * @since 22/01/2017.
 */
public class CharInfoFragment extends Fragment{

    private StarWarsChar starWarsChar;
    private View rootView;
    private View loadingIndicator;

    StarWarsPlanetAsyncTask starWarsPlanetTask;
    StarWarsSpecieAsyncTask starWarsSpecieTask;

    public CharInfoFragment() {
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
        rootView  = inflater.inflate(R.layout.pager_info,container,false);
        loadingIndicator = rootView.findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        SQLUtils db = new SQLUtils(rootView.getContext());
        fillFields(starWarsChar);

        StarWarsPlanet planet = db.getStarWarsPlanets(starWarsChar.getName());
        if(planet.getName() == null) {
            loadingIndicator.setVisibility(View.VISIBLE);
            starWarsPlanetTask = new StarWarsPlanetAsyncTask();
            starWarsPlanetTask.execute();
        }

        StarWarsSpecie specie = db.getStarWarsSpecies(starWarsChar.getName());
        if(specie.getName() == null){
            loadingIndicator.setVisibility(View.VISIBLE);
            starWarsSpecieTask = new StarWarsSpecieAsyncTask();
            starWarsSpecieTask.execute();
        }

        if (specie.getName() != null & planet.getName() != null){
            fillHomeworld(planet);
            fillSpecie(specie);
        }

        return rootView;
    }
    
    private void fillFields(StarWarsChar starWarsChar){

        TextView name = (TextView) rootView.findViewById(R.id.name);
        TextView height = (TextView) rootView.findViewById(R.id.height);
        TextView mass = (TextView) rootView.findViewById(R.id.mass);
        TextView hair_color = (TextView) rootView.findViewById(R.id.hair_color);
        TextView skin_color = (TextView) rootView.findViewById(R.id.skin_color);
        TextView eye_color = (TextView) rootView.findViewById(R.id.eye_color);
        TextView birth_year = (TextView) rootView.findViewById(R.id.birth_year);
        TextView gender = (TextView) rootView.findViewById(R.id.gender);
        TextView homeworld = (TextView) rootView.findViewById(R.id.homeworld);
        TextView url = (TextView) rootView.findViewById(R.id.url);
        TextView date = (TextView) rootView.findViewById(R.id.date);
        TextView time = (TextView) rootView.findViewById(R.id.time);
        TextView specie = (TextView) rootView.findViewById(R.id.specie);

        name.setText(starWarsChar.getName());
        height.setText(starWarsChar.getHeight());
        mass.setText(starWarsChar.getMass());
        hair_color.setText(starWarsChar.getHair_color());
        skin_color.setText(starWarsChar.getSkin_color());
        eye_color.setText(starWarsChar.getEye_color());
        birth_year.setText(starWarsChar.getBirth_year());
        gender.setText(starWarsChar.getGender());
        homeworld.setText(getString(R.string.loading));
        url.setText(starWarsChar.getUrl());
        date.setText(starWarsChar.getDate());
        time.setText(starWarsChar.getTime());
        specie.setText(getString(R.string.loading));

    }

    private void fillHomeworld(StarWarsPlanet planet){

        TextView homeworld = (TextView) rootView.findViewById(R.id.homeworld);

        homeworld.setText(planet.getName());
    }

    private void fillSpecie(StarWarsSpecie sp){

        TextView specie = (TextView) rootView.findViewById(R.id.specie);

        specie.setText(sp.getName());

    }

    //region Star Wars Planets AsyncTask
    private class StarWarsPlanetAsyncTask extends AsyncTask<Void, Void, StarWarsPlanet> {

        @Override
        protected StarWarsPlanet doInBackground(Void... urls) {
            if(starWarsChar.getHomeworld() == null){
                return null;
            }

            return ApiUtils.fetchPlanetData(starWarsChar.getHomeworld());
        }

        @Override
        protected void onPostExecute(StarWarsPlanet starWarsPlanet) {
            starWarsPlanetTask = null;
            if(starWarsPlanet != null) {
                SQLUtils db = new SQLUtils(rootView.getContext());
                db.insertStarWarsPlanets(starWarsPlanet, starWarsChar.getName());
                fillHomeworld(starWarsPlanet);
                if (starWarsSpecieTask == null) {
                    loadingIndicator.setVisibility(View.GONE);
                }
            }
        }
    }
    //endregion

    //region Star Wars Specie AsyncTask
    private class StarWarsSpecieAsyncTask extends AsyncTask<Void, Void, StarWarsSpecie> {

        @Override
        protected StarWarsSpecie doInBackground(Void... urls) {
            if(starWarsChar.getHomeworld() == null){
                return null;
            }

            return ApiUtils.fetchSpecieData(starWarsChar.getSpecies());
        }

        @Override
        protected void onPostExecute(StarWarsSpecie specie) {
            starWarsSpecieTask = null;
            if(specie != null) {
                SQLUtils db = new SQLUtils(rootView.getContext());
                db.insertStarWarsSpecies(specie, starWarsChar.getName());
                fillSpecie(specie);
                if (starWarsPlanetTask == null) {
                    loadingIndicator.setVisibility(View.GONE);
                }
            }
        }
    }
    //endregion
}
