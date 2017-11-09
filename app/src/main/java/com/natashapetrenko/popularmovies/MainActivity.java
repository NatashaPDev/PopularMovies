package com.natashapetrenko.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.natashapetrenko.popularmovies.data.MovieContract;
import com.natashapetrenko.popularmovies.utilities.NetworkUtils;
import com.natashapetrenko.popularmovies.utilities.OpenMoviesJsonUtils;

import java.net.URL;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity implements ForecastAdapter.ListItemClickListener {
    private static final String TAG = "MainActivity";
    private static final String STRING_CASHED_PATH = "CashedPath";
    private static String cashedPath = NetworkUtils.SORT_POPULAR_PATH;

    private RecyclerView mRecyclerView;
    private ForecastAdapter mAdapter;
    private boolean mFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRecyclerView = findViewById(R.id.rv_movies_list);

        mAdapter = new ForecastAdapter(this, this);

        int orientation = getResources().getConfiguration().orientation;
        int spanCount = orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 3;

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, spanCount));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        if (savedInstanceState != null) {
            cashedPath = savedInstanceState.getString(STRING_CASHED_PATH);
        }
        loadMovies(cashedPath);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(STRING_CASHED_PATH, cashedPath);
        super.onSaveInstanceState(outState);
    }

    private void loadMovies(String path) {

        if (isOnline()) {
            new FetchMoviesTask().execute(path);
        }
    }

    @Override
    public void onItemClick(Movie movie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("Id", movie.getId());
        intent.putExtra("Title", movie.getTitle());
        intent.putExtra("ImagePath", movie.getImagePath());
        intent.putExtra("UserRating", movie.getUserRating());
        intent.putExtra("ReleaseDate", movie.getReleaseDate());
        intent.putExtra("PlotSynopsis", movie.getPlotSynopsys());

        startActivity(intent);
    }

    private class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {
        @Override
        protected List<Movie> doInBackground(String... strings) {

            String path = strings[0];
            URL requestUrl = NetworkUtils.buildUrl(path);
            List<Movie> movieList;

            try {
                String jsonMoviesResponse = NetworkUtils
                        .getResponseFromHttpUrl(requestUrl);

                Log.d(TAG, "doInBackground: " + jsonMoviesResponse);

                movieList = OpenMoviesJsonUtils.getMoviesFromJson(jsonMoviesResponse);
                cashedPath = path;

                if (mFavorite) {
                    Cursor cursor = getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, null, null, null, null);
                    if (cursor.getCount() > 0) {
                        while (cursor.moveToNext()) {
                            ListIterator<Movie> iterator = movieList.listIterator();
                            while (iterator.hasNext()) {
                                //iterator.next();
                                if (iterator.next().getId() != Integer.parseInt(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry._ID)))) {
                                    iterator.remove();
                                }
                            }
                        }
                    } else {
                        movieList.clear();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            return movieList;
        }

        @Override
        protected void onPostExecute(List<Movie> moviesList) {
            super.onPostExecute(moviesList);
            mAdapter.setMovieList(moviesList);
            Log.d(TAG, "onPostExecute: " + moviesList);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_popular:
                loadMovies(NetworkUtils.SORT_POPULAR_PATH);
                item.setChecked(true);
                mFavorite = false;
                return true;
            case R.id.action_top_rated:
                loadMovies(NetworkUtils.SORT_TOP_RATED_PATH);
                item.setChecked(true);
                mFavorite = false;
                return true;
            case R.id.action_favorite:
                loadMovies(cashedPath);
                item.setChecked(true);
                mFavorite = true;
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm != null ? cm.getActiveNetworkInfo() : null;
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
