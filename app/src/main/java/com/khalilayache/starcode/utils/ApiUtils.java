package com.khalilayache.starcode.utils;

import android.text.TextUtils;
import android.util.Log;

import com.khalilayache.starcode.models.StarWarsChar;
import com.khalilayache.starcode.models.StarWarsFilm;
import com.khalilayache.starcode.models.StarWarsPlanet;
import com.khalilayache.starcode.models.StarWarsShip;
import com.khalilayache.starcode.models.StarWarsSpecie;
import com.khalilayache.starcode.models.StarWarsVehicle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * @author USUARIO
 * @since 21/01/2017.
 */
public final class ApiUtils {

    private static boolean hasNext = true;

    //region Generic API Methods
    private static URL createURL(String stringURL){
        URL url = null;
        try{
            url = new URL(stringURL);
        }catch (MalformedURLException e) {
            Log.e("StarCode","Problem building the URL", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if(url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else{
                Log.e("StarCode ERROR","Error response code: " + urlConnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e("StarCode ERROR","Problem retrieving the earthquake JSON results", e);
        }
        finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if(inputStream != null){
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    //endregion

    //region StarWars Chars API Methods
    public static StarWarsChar fetchCharData (String requestURL){

        URL url =  createURL(requestURL);

        String jsonResponse = null;

        try{
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("StarCode ERROR","Problem making the HTTP request", e);
        }

        return extractCharsFromJson(jsonResponse);
    }

    private static StarWarsChar extractCharsFromJson(String charJSON){

        if(TextUtils.isEmpty(charJSON)){
            return null;
        }

        StarWarsChar starWarsChar = new StarWarsChar();

        try {
            JSONObject root = new JSONObject(charJSON);
            starWarsChar.setName(root.getString("name"));
            Double height = (root.getDouble("height")/100);
            starWarsChar.setHeight(height.toString());
            starWarsChar.setMass(root.getString("mass"));
            starWarsChar.setHair_color(root.getString("hair_color"));
            starWarsChar.setSkin_color(root.getString("skin_color"));
            starWarsChar.setEye_color(root.getString("eye_color"));
            starWarsChar.setBirth_year(root.getString("birth_year"));
            starWarsChar.setGender(root.getString("gender"));
            starWarsChar.setHomeworld(root.getString("homeworld"));
            starWarsChar.setUrl(root.getString("url"));
            starWarsChar.setDate(StringUtils.formatDate(getTimeNow()));
            starWarsChar.setTime(StringUtils.formatTime(getTimeNow()));

            JSONArray filmsArray = root.getJSONArray("films");
            ArrayList<String> films = new ArrayList<>();
            for(int i = 0; i < filmsArray.length();i++) {
                String film = filmsArray.getString(i);
                films.add(film);
            }
            if (films.size()>0)
                starWarsChar.setFilms(films);

            JSONArray starshipsArray = root.getJSONArray("starships");
            ArrayList<String> starships = new ArrayList<>();
            for(int i = 0; i < starshipsArray.length();i++) {
                String starship = starshipsArray.getString(i);
                starships.add(starship);
            }
            if (starships.size()>0)
                starWarsChar.setStarships(starships);

            JSONArray vehiclesArray = root.getJSONArray("vehicles");
            ArrayList<String> vehicles = new ArrayList<>();
            for(int i = 0; i < vehiclesArray.length();i++) {
                String vehicle = vehiclesArray.getString(i);
                vehicles.add(vehicle);
            }
            if (vehicles.size()>0)
                starWarsChar.setVehicles(vehicles);

            JSONArray speciesArray = root.getJSONArray("species");
            if (speciesArray.length()>0)
                starWarsChar.setSpecies(speciesArray.getString(0));

            /*


            private ArrayList<String> species;*/
            //JSONArray earthquakeArray = root.getJSONArray("features");


        } catch (JSONException e) {
            Log.e("StarCode ERROR","Problem parsing the earthquake JSON results", e);
        }

        return starWarsChar;
    }
    //endregion

    //region StarWars Valid URL Methods
    public static ArrayList<String> fetchValidURLData(String requestURL) {

        ArrayList<String> validURLs = new ArrayList<>();
        int cont = 1;

        while(hasNext){
            ArrayList<String> fetchedURLs;
            URL url;
            if(cont == 1)
                 url = createURL(requestURL);
            else
                 url = createURL(requestURL + "?page=" + cont);

            String jsonResponse = null;

            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                Log.e("StarCode ERROR", "Problem making the HTTP request", e);
            }
            fetchedURLs = extractURLsFromJson(jsonResponse);

            for (int i = 0; i < fetchedURLs.size(); i++){
                validURLs.add(fetchedURLs.get(i));
            }
            cont++;
        }

        //return extractURLsFromJson(jsonResponse);
        return  validURLs;
    }

    private static ArrayList<String> extractURLsFromJson(String charJSON){

        if(TextUtils.isEmpty(charJSON)){
            return null;
        }

        ArrayList<String> validUrls = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(charJSON);
            JSONArray results =  root.getJSONArray("results");


            for(int i = 0; i < results.length(); i ++ ){
                JSONObject currentItem = results.getJSONObject(i);
                String url = currentItem.getString("url");
                validUrls.add(url);
            }

            String next = root.getString("next");
            if(!next.contains("http://swapi.co/api/people/"))
                hasNext = false;

        } catch (JSONException e) {
            Log.e("StarCode ERROR","Problem parsing the earthquake JSON results", e);
        }

        return validUrls;
    }

    //endregion

    //region StarWars Planets Methods
    public static StarWarsPlanet fetchPlanetData(String requestURL) {

        URL url =  createURL(requestURL);

        String jsonResponse = null;

        try{
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("StarCode ERROR","Problem making the HTTP request", e);
        }


        return extractPlanetFromJson(jsonResponse);
    }

    private static StarWarsPlanet extractPlanetFromJson(String charJSON){

        if(TextUtils.isEmpty(charJSON)){
            return null;
        }

        StarWarsPlanet planet = new StarWarsPlanet();

        try {
            JSONObject root = new JSONObject(charJSON);

            planet.setName(root.getString("name"));

        } catch (JSONException e) {
            Log.e("StarCode ERROR","Problem parsing the earthquake JSON results", e);
        }

        return planet;
    }


    //endregion

    //region StarWars Species Methods
    public static StarWarsSpecie fetchSpecieData(String requestURL) {

        URL url =  createURL(requestURL);

        String jsonResponse = null;

        try{
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("StarCode ERROR","Problem making the HTTP request", e);
        }


        return extractSpecieFromJson(jsonResponse);
    }

    private static StarWarsSpecie extractSpecieFromJson(String charJSON){

        if(TextUtils.isEmpty(charJSON)){
            return null;
        }

        StarWarsSpecie specie = new StarWarsSpecie();

        try {
            JSONObject root = new JSONObject(charJSON);

            specie.setName(root.getString("name"));

        } catch (JSONException e) {
            Log.e("StarCode ERROR","Problem parsing the earthquake JSON results", e);
        }

        return specie;
    }


    //endregion

    //region StarWars Films Methods
    public static ArrayList<StarWarsFilm> fetchFilmsData(ArrayList<String> urlFilms) {


        ArrayList<StarWarsFilm> starWarsFilms = new ArrayList<>();
        for(int i = 0; i < urlFilms.size(); i++) {
            URL url = createURL(urlFilms.get(i));

            String jsonResponse = null;

            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                Log.e("StarCode ERROR", "Problem making the HTTP request", e);
            }
            starWarsFilms.add(extractFilmFromJson(jsonResponse));
        }

        return starWarsFilms;
    }

    private static StarWarsFilm extractFilmFromJson(String charJSON){

        if(TextUtils.isEmpty(charJSON)){
            return null;
        }

        StarWarsFilm film = new StarWarsFilm();

        try {
            JSONObject root = new JSONObject(charJSON);
            film.setTitle(root.getString("title"));
            film.setEpisode_id(root.getString("episode_id"));
            film.setDirector(root.getString("director"));
            film.setProducer(root.getString("producer"));
            film.setRelease_date(StringUtils.formatDateFromApi(root.getString("release_date")));

        } catch (JSONException e) {
            Log.e("StarCode ERROR","Problem parsing the earthquake JSON results", e);
        }

        return film;
    }


    //endregion

    //region StarWars Ships Methods
    public static ArrayList<StarWarsShip> fetchShipsData(ArrayList<String> urlShips) {


        ArrayList<StarWarsShip> starWarsShips = new ArrayList<>();
        for(int i = 0; i < urlShips.size(); i++) {
            URL url = createURL(urlShips.get(i));

            String jsonResponse = null;

            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                Log.e("StarCode ERROR", "Problem making the HTTP request", e);
            }
            starWarsShips.add(extractShipFromJson(jsonResponse));
        }
        return starWarsShips;
    }

    private static StarWarsShip extractShipFromJson(String charJSON){

        if(TextUtils.isEmpty(charJSON)){
            return null;
        }

        StarWarsShip ship = new StarWarsShip();

        try {
            JSONObject root = new JSONObject(charJSON);
            ship.setName(root.getString("name"));
            ship.setModel(root.getString("model"));

        } catch (JSONException e) {
            Log.e("StarCode ERROR","Problem parsing the earthquake JSON results", e);
        }

        return ship;
    }


    //endregion

    //region StarWars Vehicles Methods
    public static ArrayList<StarWarsVehicle> fetchVehicleData(ArrayList<String> urlVehicle) {


        ArrayList<StarWarsVehicle> starWarsVehicles = new ArrayList<>();
        for(int i = 0; i < urlVehicle.size(); i++) {
            URL url = createURL(urlVehicle.get(i));

            String jsonResponse = null;

            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                Log.e("StarCode ERROR", "Problem making the HTTP request", e);
            }
            starWarsVehicles.add(extractVehicleFromJson(jsonResponse));
        }
        return starWarsVehicles;
    }

    private static StarWarsVehicle extractVehicleFromJson(String charJSON){

        if(TextUtils.isEmpty(charJSON)){
            return null;
        }

        StarWarsVehicle vehicle = new StarWarsVehicle();

        try {
            JSONObject root = new JSONObject(charJSON);
            vehicle.setName(root.getString("name"));
            vehicle.setModel(root.getString("model"));

        } catch (JSONException e) {
            Log.e("StarCode ERROR","Problem parsing the earthquake JSON results", e);
        }

        return vehicle;
    }


    //endregion

    //region Other Methods
    private static Date getTimeNow() {
        Calendar c = Calendar.getInstance();
        return c.getTime();
    }
    //endregion


}
