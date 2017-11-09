package com.natashapetrenko.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by petrenkonv on 09.11.2017.
 */

public class MovieContract {

    public static final String AUTHORITY = "com.natashapetrenko.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_MOVIES = "movies";

    public static final class MovieEntry implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        public static final String TABLE_MOVIES = "movies";

        public static final String COLUMN_TITLE = "title";

    }
}
