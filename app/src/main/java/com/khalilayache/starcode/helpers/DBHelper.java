package com.khalilayache.starcode.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author USUARIO
 * @since 22/01/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "StarCodeDB";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //region StarWarsChar Table
        String sql = "CREATE TABLE Chars (" +
                "name TEXT," +
                "height TEXT," +
                "mass TEXT," +
                "hair_color TEXT," +
                "skin_color TEXT," +
                "eye_color TEXT," +
                "birth_year TEXT," +
                "gender TEXT," +
                "homeworld TEXT," +
                "url TEXT," +
                "date TEXT," +
                "time TEXT);";
        db.execSQL(sql);
        //endregion


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
