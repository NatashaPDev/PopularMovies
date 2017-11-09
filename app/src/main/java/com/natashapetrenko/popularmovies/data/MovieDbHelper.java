package com.natashapetrenko.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by petrenkonv on 09.11.2017.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movies.db";
    public static final int DATABASE_VERSION = 1;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sSql = "CREATE TABLE IF NOT EXISTS " + MovieContract.MovieEntry.TABLE_MOVIES + " ("
                + MovieContract.MovieEntry._ID + " TEXT NOT NULL, "
                + MovieContract.MovieEntry.COLUMN_TITLE + " TEXT NOT NULL)";

        sqLiteDatabase.execSQL(sSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
