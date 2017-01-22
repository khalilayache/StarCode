package com.khalilayache.starcode.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.khalilayache.starcode.helpers.DBHelper;
import com.khalilayache.starcode.models.StarWarsChar;

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
}
