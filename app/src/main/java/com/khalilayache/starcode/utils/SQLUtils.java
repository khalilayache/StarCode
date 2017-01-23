package com.khalilayache.starcode.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.khalilayache.starcode.helpers.DBHelper;
import com.khalilayache.starcode.models.StarWarsChar;
import com.khalilayache.starcode.models.StarWarsFilm;
import com.khalilayache.starcode.models.StarWarsPlanet;
import com.khalilayache.starcode.models.StarWarsShip;
import com.khalilayache.starcode.models.StarWarsSpecie;
import com.khalilayache.starcode.models.StarWarsVehicle;

import java.util.ArrayList;

/**
 * @author USUARIO
 * @since 22/01/2017.
 */

public final class SQLUtils extends DBHelper {

    public SQLUtils(Context context) {
        super(context);
    }

    //region StarWarsChars Methods
    public void insertStarWarsChar(StarWarsChar starWarsChar){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = getContentValuesStarWarsChar(starWarsChar);
        db.insert("Chars",null, values);
        db.close();

        if(starWarsChar.getFilms() != null){
            if(starWarsChar.getFilms().size()>0)
                insertStarWarsURLFilms(starWarsChar);
        }

        if(starWarsChar.getStarships() != null){
            if(starWarsChar.getStarships().size() >0)
                insertStarWarsURLShips(starWarsChar);
        }

        if(starWarsChar.getVehicles() != null){
            if(starWarsChar.getVehicles().size() >0)
                insertStarWarsURLVehicles(starWarsChar);
        }
    }

    public void updateStarWarsChar(StarWarsChar starWarsChar){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = getContentValuesStarWarsChar(starWarsChar);
        String[] params = {starWarsChar.getName()};
        db.update("Chars",values,"name = ?", params);
        db.close();

        if(starWarsChar.getFilms() != null){
            if(starWarsChar.getFilms().size()>0)
                updateStarWarsURLFilms(starWarsChar);
        }

        if(starWarsChar.getStarships() != null){
            if(starWarsChar.getStarships().size() >0)
                updateStarWarsURLShips(starWarsChar);
        }

        if(starWarsChar.getVehicles() != null){
            if(starWarsChar.getVehicles().size() >0)
                updateStarWarsURLVehicles(starWarsChar);
        }
    }

    private ContentValues getContentValuesStarWarsChar(StarWarsChar starWarsChar) {
        ContentValues values = new ContentValues();
        values.put("name", starWarsChar.getName());
        values.put("height", starWarsChar.getHeight());
        values.put("mass", starWarsChar.getMass());
        values.put("hair_color", starWarsChar.getHair_color());
        values.put("skin_color", starWarsChar.getSkin_color());
        values.put("eye_color", starWarsChar.getEye_color());
        values.put("birth_year", starWarsChar.getBirth_year());
        values.put("gender", starWarsChar.getGender());
        values.put("homeworld", starWarsChar.getHomeworld());
        values.put("url", starWarsChar.getUrl());
        values.put("date", starWarsChar.getDate());
        values.put("time", starWarsChar.getTime());
        values.put("species",starWarsChar.getSpecies());

        return values;
    }

    public ArrayList<StarWarsChar> getAllStarWarsChars(){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<StarWarsChar> charArrayList = new ArrayList<>();
        String sql = "SELECT * FROM Chars";
        Cursor c = db.rawQuery(sql, null);
        if (c.getCount() > 0) {
            while (c.moveToNext()){
                StarWarsChar starWarsChar = new StarWarsChar();
                starWarsChar.setName(c.getString(c.getColumnIndex("name")));
                starWarsChar.setHeight(c.getString(c.getColumnIndex("height")));
                starWarsChar.setMass(c.getString(c.getColumnIndex("mass")));
                starWarsChar.setHair_color(c.getString(c.getColumnIndex("hair_color")));
                starWarsChar.setSkin_color(c.getString(c.getColumnIndex("skin_color")));
                starWarsChar.setEye_color(c.getString(c.getColumnIndex("eye_color")));
                starWarsChar.setBirth_year(c.getString(c.getColumnIndex("birth_year")));
                starWarsChar.setGender(c.getString(c.getColumnIndex("gender")));
                starWarsChar.setHomeworld(c.getString(c.getColumnIndex("homeworld")));
                starWarsChar.setUrl(c.getString(c.getColumnIndex("url")));
                starWarsChar.setDate(c.getString(c.getColumnIndex("date")));
                starWarsChar.setTime(c.getString(c.getColumnIndex("time")));
                starWarsChar.setSpecies(c.getString(c.getColumnIndex("species")));
                starWarsChar.setFilms(getAllStarWarsURLFilmsString(starWarsChar));
                starWarsChar.setStarships(getAllStarWarsURLShipsString(starWarsChar));
                starWarsChar.setVehicles(getAllStarWarsURLVehiclesString(starWarsChar));
                charArrayList.add(starWarsChar);
            }
        }
        c.close();
        db.close();
        return charArrayList;
    }

    public StarWarsChar getStarWarsChar(StarWarsChar starWarsChar){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM Chars where name = ?";
        String[] params = {starWarsChar.getName()};
        Cursor c = db.rawQuery(sql, params);
        StarWarsChar warsChar = new StarWarsChar();
        if (c.getCount() > 0) {
            while (c.moveToNext()){
                warsChar.setName(c.getString(c.getColumnIndex("name")));
                warsChar.setHeight(c.getString(c.getColumnIndex("height")));
                warsChar.setMass(c.getString(c.getColumnIndex("mass")));
                warsChar.setHair_color(c.getString(c.getColumnIndex("hair_color")));
                warsChar.setSkin_color(c.getString(c.getColumnIndex("skin_color")));
                warsChar.setEye_color(c.getString(c.getColumnIndex("eye_color")));
                warsChar.setBirth_year(c.getString(c.getColumnIndex("birth_year")));
                warsChar.setGender(c.getString(c.getColumnIndex("gender")));
                warsChar.setHomeworld(c.getString(c.getColumnIndex("homeworld")));
                warsChar.setUrl(c.getString(c.getColumnIndex("url")));
                warsChar.setDate(c.getString(c.getColumnIndex("date")));
                warsChar.setTime(c.getString(c.getColumnIndex("time")));
                warsChar.setSpecies(c.getColumnName(c.getColumnIndex("species")));
                warsChar.setFilms(getAllStarWarsURLFilmsString(starWarsChar));
                starWarsChar.setStarships(getAllStarWarsURLShipsString(starWarsChar));
                starWarsChar.setVehicles(getAllStarWarsURLVehiclesString(starWarsChar));
            }
        }
        c.close();
        db.close();
        return warsChar;
    }

    //endregion

    //region StarWarsValidURLs Methods
    public void insertStarWarsValidURLs(ArrayList<String> validURLs) {
        SQLiteDatabase db = getWritableDatabase();

        db.delete("ValidURLs",null,null);
        for (int i = 0; i < validURLs.size(); i++) {
            ContentValues values = getContentValuesStarWarsValidURLs(validURLs.get(i));
            db.insert("ValidURLs",null, values);
        }

        db.close();

    }

    private ContentValues getContentValuesStarWarsValidURLs(String url) {

        ContentValues values = new ContentValues();
        values.put("url", url);

        return values;
    }

    public ArrayList<String> getAllStarWarsValidURL(){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> urlList = new ArrayList<>();
        String sql = "SELECT * FROM ValidURLs";
        Cursor c = db.rawQuery(sql, null);
        if (c.getCount() > 0) {
            while (c.moveToNext()){
                String validURL;
                validURL = c.getString(c.getColumnIndex("url"));
                urlList.add(validURL);
            }
        }
        c.close();
        db.close();
        return urlList;
    }

    public boolean getStarWarsValidURL(String url){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM ValidURLs where url = ?";
        String[] params = {url};
        Cursor c = db.rawQuery(sql, params);
        if (c.getCount() > 0) {
            return  true;
        }
        c.close();
        db.close();
        return false;
    }

    //endregion

    //region StarWarsPlanets Methods
    public void insertStarWarsPlanets(StarWarsPlanet planet, String charName){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = getContentValuesStarWarsPlanets(planet,charName);
        db.insert("Planets",null, values);
        db.close();
    }

    private ContentValues getContentValuesStarWarsPlanets(StarWarsPlanet planet, String charName) {
        ContentValues values = new ContentValues();
        values.put("planetName", planet.getName());
        values.put("charName", charName);

        return values;
    }

    public StarWarsPlanet getStarWarsPlanets(String name){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM Planets where charName = ?";
        String[] params = {name};
        Cursor c = db.rawQuery(sql, params);
        StarWarsPlanet planet = new StarWarsPlanet();
        if (c.getCount() > 0) {
            while (c.moveToNext()){
                planet.setName(c.getString(c.getColumnIndex("planetName")));
            }
        }
        c.close();
        db.close();
        return planet;
    }

    //endregion

    //region StarWarsSpecies Methods
    public void insertStarWarsSpecies(StarWarsSpecie specie, String charName){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = getContentValuesStarWarsSpecies(specie,charName);
        db.insert("Species",null, values);
        db.close();
    }

    private ContentValues getContentValuesStarWarsSpecies(StarWarsSpecie specie, String charName) {
        ContentValues values = new ContentValues();
        values.put("specieName", specie.getName());
        values.put("charName", charName);

        return values;
    }

    public StarWarsSpecie getStarWarsSpecies(String name){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM Species where charName = ?";
        String[] params = {name};
        Cursor c = db.rawQuery(sql, params);
        StarWarsSpecie specie = new StarWarsSpecie();
        if (c.getCount() > 0) {
            while (c.moveToNext()){
                specie.setName(c.getString(c.getColumnIndex("specieName")));
            }
        }
        c.close();
        db.close();
        return specie;
    }

    //endregion

    //region StarWarsFilms Methods
    public void insertStarWarsFilms(ArrayList<StarWarsFilm> starWarsFilms, String charName){
        SQLiteDatabase db = getWritableDatabase();
        for(int i = 0; i < starWarsFilms.size(); i++) {
            ContentValues values = getContentValuesStarWarsFilms(starWarsFilms.get(i), charName);
            db.insert("Films", null, values);
        }
        db.close();
    }

    private ContentValues getContentValuesStarWarsFilms(StarWarsFilm film, String charName) {
        ContentValues values = new ContentValues();
        values.put("charName", charName);
        values.put("title", film.getTitle());
        values.put("director", film.getDirector());
        values.put("episode", film.getEpisode_id());
        values.put("releaseDate", film.getRelease_date());
        values.put("producer", film.getProducer());
        return values;
    }

    public ArrayList<StarWarsFilm> getAllStarWarsFilms(String name){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM Films where charName = ?";
        String[] params = {name};
        Cursor c = db.rawQuery(sql, params);
        ArrayList<StarWarsFilm> films = new ArrayList<>();
        if (c.getCount() > 0) {
            while (c.moveToNext()){
                StarWarsFilm film = new StarWarsFilm();
                film.setDirector(c.getString(c.getColumnIndex("director")));
                film.setProducer(c.getString(c.getColumnIndex("producer")));
                film.setEpisode_id(c.getString(c.getColumnIndex("episode")));
                film.setRelease_date(c.getString(c.getColumnIndex("releaseDate")));
                film.setTitle(c.getString(c.getColumnIndex("title")));
                films.add(film);
            }
        }
        c.close();
        db.close();
        return films;
    }

    //endregion

    //region StarWarsShips Methods
    public void insertStarWarsShips(ArrayList<StarWarsShip> starWarsShips, String charName){
        SQLiteDatabase db = getWritableDatabase();
        for(int i = 0; i < starWarsShips.size(); i++) {
            ContentValues values = getContentValuesStarWarsShips(starWarsShips.get(i), charName);
            db.insert("Starships", null, values);
        }
        db.close();
    }

    private ContentValues getContentValuesStarWarsShips(StarWarsShip ship, String charName) {
        ContentValues values = new ContentValues();
        values.put("charName", charName);
        values.put("name", ship.getName());
        values.put("model", ship.getModel());
        return values;
    }

    public ArrayList<StarWarsShip> getAllStarWarsShips(String name){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM Starships where charName = ?";
        String[] params = {name};
        Cursor c = db.rawQuery(sql, params);
        ArrayList<StarWarsShip> ships = new ArrayList<>();
        if (c.getCount() > 0) {
            while (c.moveToNext()){
                StarWarsShip ship = new StarWarsShip();
                ship.setName(c.getString(c.getColumnIndex("name")));
                ship.setModel(c.getString(c.getColumnIndex("model")));
                ships.add(ship);
            }
        }
        c.close();
        db.close();
        return ships;
    }

    //endregion

    //region StarWarsVehicle Methods
    public void insertStarWarsVehicle(ArrayList<StarWarsVehicle> starWarsVehicles, String charName){
        SQLiteDatabase db = getWritableDatabase();
        for(int i = 0; i < starWarsVehicles.size(); i++) {
            ContentValues values = getContentValuesStarWarsVehicle(starWarsVehicles.get(i), charName);
            db.insert("Vehicles", null, values);
        }
        db.close();
    }

    private ContentValues getContentValuesStarWarsVehicle(StarWarsVehicle vehicle, String charName) {
        ContentValues values = new ContentValues();
        values.put("charName", charName);
        values.put("name", vehicle.getName());
        values.put("model", vehicle.getModel());
        return values;
    }

    public ArrayList<StarWarsVehicle> getAllStarWarsVehicle(String name){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM Vehicles where charName = ?";
        String[] params = {name};
        Cursor c = db.rawQuery(sql, params);
        ArrayList<StarWarsVehicle> vehicles = new ArrayList<>();
        if (c.getCount() > 0) {
            while (c.moveToNext()){
                StarWarsVehicle vehicle = new StarWarsVehicle();
                vehicle.setName(c.getString(c.getColumnIndex("name")));
                vehicle.setModel(c.getString(c.getColumnIndex("model")));
                vehicles.add(vehicle);
            }
        }
        c.close();
        db.close();
        return vehicles;
    }

    //endregion

    //region StarWarsURLFilms Methods
    public void insertStarWarsURLFilms(StarWarsChar starWarsChar){
        SQLiteDatabase db = getWritableDatabase();
        for(int i = 0; i < starWarsChar.getFilms().size(); i++) {
            ContentValues values = getContentValuesStarWarsURLFilms(starWarsChar.getFilms().get(i), starWarsChar.getName());
            db.insert("CharFilms", null, values);
        }

        db.close();

    }

    public void updateStarWarsURLFilms(StarWarsChar starWarsChar){
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {starWarsChar.getName()};
        db.delete("CharFilms","charName = ?",params);
        db.delete("Films","charName = ?",params);
        db.close();
        insertStarWarsURLFilms(starWarsChar);
    }

    private ContentValues getContentValuesStarWarsURLFilms(String urlFilm, String charName) {
        ContentValues values = new ContentValues();
        values.put("charName", charName);
        values.put("filmURL", urlFilm);

        return values;
    }

    public ArrayList<String> getAllStarWarsURLFilmsString(StarWarsChar starWarsChar){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> filmsArrayList = new ArrayList<>();
        String sql = "SELECT * FROM CharFilms where charName = ?";
        String[] params = {starWarsChar.getName()};
        Cursor c = db.rawQuery(sql, params);
        if (c.getCount() > 0) {
            while (c.moveToNext()){
                StarWarsFilm starWarsFilm = new StarWarsFilm();
                filmsArrayList.add(c.getString(c.getColumnIndex("filmURL")));
            }
        }
        c.close();
        db.close();
        return filmsArrayList;
    }

    //endregion

    //region StarWarsURLShips Methods
    public void insertStarWarsURLShips(StarWarsChar starWarsChar){
        SQLiteDatabase db = getWritableDatabase();
        for(int i = 0; i < starWarsChar.getStarships().size(); i++) {
            ContentValues values = getContentValuesStarWarsURLShips(starWarsChar.getStarships().get(i), starWarsChar.getName());
            db.insert("CharStarships", null, values);
        }

        db.close();

    }

    public void updateStarWarsURLShips(StarWarsChar starWarsChar){
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {starWarsChar.getName()};
        db.delete("CharStarships","charName = ?",params);
        db.delete("Starships","charName = ?",params);
        db.close();
        insertStarWarsURLFilms(starWarsChar);
    }

    private ContentValues getContentValuesStarWarsURLShips(String urlShip, String charName) {
        ContentValues values = new ContentValues();
        values.put("charName", charName);
        values.put("starshipURL", urlShip);

        return values;
    }

    public ArrayList<String> getAllStarWarsURLShipsString(StarWarsChar starWarsChar){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> shipsArrayList = new ArrayList<>();
        String sql = "SELECT * FROM CharStarships where charName = ?";
        String[] params = {starWarsChar.getName()};
        Cursor c = db.rawQuery(sql, params);
        if (c.getCount() > 0) {
            while (c.moveToNext()){
                shipsArrayList.add(c.getString(c.getColumnIndex("starshipURL")));
            }
        }
        c.close();
        db.close();
        return shipsArrayList;
    }

    //endregion

    //region StarWarsURLVehicles Methods
    public void insertStarWarsURLVehicles(StarWarsChar starWarsChar){
        SQLiteDatabase db = getWritableDatabase();
        for(int i = 0; i < starWarsChar.getVehicles().size(); i++) {
            ContentValues values = getContentValuesStarWarsURLVehicles(starWarsChar.getVehicles().get(i), starWarsChar.getName());
            db.insert("CharVehicles", null, values);
        }

        db.close();
    }

    public void updateStarWarsURLVehicles(StarWarsChar starWarsChar){
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {starWarsChar.getName()};
        db.delete("CharVehicles","charName = ?",params);
        db.delete("Vehicles","charName = ?",params);
        db.close();
        insertStarWarsURLVehicles(starWarsChar);
    }

    private ContentValues getContentValuesStarWarsURLVehicles(String urlVehicle, String charName) {
        ContentValues values = new ContentValues();
        values.put("charName", charName);
        values.put("vehicleURL", urlVehicle);

        return values;
    }

    public ArrayList<String> getAllStarWarsURLVehiclesString(StarWarsChar starWarsChar){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> vehiclesArrayList = new ArrayList<>();
        String sql = "SELECT * FROM CharVehicles where charName = ?";
        String[] params = {starWarsChar.getName()};
        Cursor c = db.rawQuery(sql, params);
        if (c.getCount() > 0) {
            while (c.moveToNext()){
                vehiclesArrayList.add(c.getString(c.getColumnIndex("vehicleURL")));
            }
        }
        c.close();
        db.close();
        return vehiclesArrayList;
    }

    //endregion

}
