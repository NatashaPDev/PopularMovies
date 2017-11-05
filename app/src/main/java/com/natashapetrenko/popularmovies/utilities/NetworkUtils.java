
package com.natashapetrenko.popularmovies.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String API_KEY =
            "";

    private static final String IMAGE_URL =
            "http://image.tmdb.org/t/p/w342";

    private static final String FORECAST_BASE_URL = "http://api.themoviedb.org/3/movie/";
    public static final String SORT_POPULAR_PATH = "popular";
    public static final String SORT_TOP_RATED_PATH = "top_rated";

    private final static String API_KEY_PARAM = "api_key";

    public static URL buildUrl(String path) {
        Uri builtUri = Uri.parse(FORECAST_BASE_URL + path).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static String getImageURL(String imagePath) {

        return IMAGE_URL + imagePath;

    }


}