
package com.natashapetrenko.popularmovies.utilities;

import android.util.Log;

import com.natashapetrenko.popularmovies.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public final class OpenMoviesJsonUtils {
    private static final String TAG = "OpenMoviesJsonUtils";

    public static List<Movie> getMoviesFromJson(String forecastJsonStr)
            throws JSONException {

        final String OWM_LIST = "results";

        final String OWM_TITLE = "title";
        final String OWM_POSTER = "poster_path";
        final String OWM_VOTE_AVERAGE = "vote_average";
        final String OWM_RELEASE_DATE = "release_date";
        final String OWM_OVERVIEW = "overview";

        List<Movie> moviesList = new ArrayList<>();

        JSONObject forecastJson = new JSONObject(forecastJsonStr);

        JSONArray moviesArray = forecastJson.getJSONArray(OWM_LIST);

        for (int i = 0; i < moviesArray.length(); i++) {
            String title;
            String imagePath;
            String releaseDate;
            String userRating;
            String plotSynopsis;

            JSONObject jsonMovie = moviesArray.getJSONObject(i);

            title = jsonMovie.getString(OWM_TITLE);
            imagePath = jsonMovie.getString(OWM_POSTER);
            releaseDate = jsonMovie.getString(OWM_RELEASE_DATE);
            userRating = jsonMovie.getString(OWM_VOTE_AVERAGE);
            plotSynopsis = jsonMovie.getString(OWM_OVERVIEW);

            moviesList.add(new Movie(title, imagePath, userRating, releaseDate, plotSynopsis));
            Log.d(TAG, "getMoviesFromJson: " + title);
        }

        return moviesList;
    }

}