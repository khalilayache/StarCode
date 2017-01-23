package com.khalilayache.starcode.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author USUARIO
 * @since 22/01/2017.
 */

public abstract class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "StarCodeDB";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //region ValidURLs Table
        String sql = "CREATE TABLE ValidURLs (" +
                "url TEXT);";
        db.execSQL(sql);

        //endregion

        //region StarWarsChar Table
         sql = "CREATE TABLE Chars (" +
                "name TEXT," +
                "height TEXT," +
                "mass TEXT," +
                "hair_color TEXT," +
                "skin_color TEXT," +
                "eye_color TEXT," +
                "birth_year TEXT," +
                "gender TEXT," +
                "species TEXT," +
                "homeworld TEXT," +
                "url TEXT," +
                "date TEXT," +
                "time TEXT);";
         db.execSQL(sql);
        //endregion

        //region Planets Table
        sql = "CREATE TABLE Planets (" +
                "charName TEXT," +
                "planetName TEXT);";
        db.execSQL(sql);

        //endregion

        //region Species Table
        sql = "CREATE TABLE Species (" +
                "charName TEXT," +
                "specieName TEXT);";
        db.execSQL(sql);

        //endregion


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE Chars";
        db.execSQL(sql);

        sql = "DROP TABLE ValidURLs";
        db.execSQL(sql);

        sql = "DROP TABLE Planets";
        db.execSQL(sql);

        sql = "DROP TABLE Species";
        db.execSQL(sql);

        onCreate(db);

    }
}
