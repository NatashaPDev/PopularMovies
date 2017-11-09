
package com.natashapetrenko.popularmovies.utilities;

import android.util.Log;

import com.natashapetrenko.popularmovies.Movie;
import com.natashapetrenko.popularmovies.Review;
import com.natashapetrenko.popularmovies.Trailer;

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

        final String OWM_ID = "id";
        final String OWM_TITLE = "title";
        final String OWM_POSTER = "poster_path";
        final String OWM_VOTE_AVERAGE = "vote_average";
        final String OWM_RELEASE_DATE = "release_date";
        final String OWM_OVERVIEW = "overview";

        List<Movie> moviesList = new ArrayList<>();

        JSONObject forecastJson = new JSONObject(forecastJsonStr);

        JSONArray moviesArray = forecastJson.getJSONArray(OWM_LIST);

        for (int i = 0; i < moviesArray.length(); i++) {
            int id;
            String title;
            String imagePath;
            String releaseDate;
            String userRating;
            String plotSynopsis;

            JSONObject jsonMovie = moviesArray.getJSONObject(i);

            id = jsonMovie.getInt(OWM_ID);
            title = jsonMovie.getString(OWM_TITLE);
            imagePath = jsonMovie.getString(OWM_POSTER);
            releaseDate = jsonMovie.getString(OWM_RELEASE_DATE);
            userRating = jsonMovie.getString(OWM_VOTE_AVERAGE);
            plotSynopsis = jsonMovie.getString(OWM_OVERVIEW);

            moviesList.add(new Movie(id, title, imagePath, userRating, releaseDate, plotSynopsis));
            Log.d(TAG, "getMoviesFromJson: " + title);
        }

        return moviesList;
    }

    public static List<Trailer> getTralersFromJson(String forecastJsonStr)
            throws JSONException {

        final String LIST = "results";

        final String ID = "key";
        final String NAME = "name";

        List<Trailer> trailersList = new ArrayList<>();

        JSONObject forecastJson = new JSONObject(forecastJsonStr);

        JSONArray moviesArray = forecastJson.getJSONArray(LIST);

        for (int i = 0; i < moviesArray.length(); i++) {
            String id;
            String name;

            JSONObject jsonMovie = moviesArray.getJSONObject(i);

            id = jsonMovie.getString(ID);
            name = jsonMovie.getString(NAME);

            trailersList.add(new Trailer(id, name));

        }

        return trailersList;
    }

    public static List<Review> getReviewsFromJson(String jsonMoviesResponse) throws JSONException {

        final String LIST = "results";

        final String ID = "url";
        final String NAME = "content";

        List<Review> reviewsList = new ArrayList<>();

        JSONObject forecastJson = new JSONObject(jsonMoviesResponse);

        JSONArray moviesArray = forecastJson.getJSONArray(LIST);

        for (int i = 0; i < moviesArray.length(); i++) {
            String url;
            String content;

            JSONObject jsonMovie = moviesArray.getJSONObject(i);

            url = jsonMovie.getString(ID);
            content = jsonMovie.getString(NAME);

            reviewsList.add(new Review(url, content));

        }

        return reviewsList;
    }
}