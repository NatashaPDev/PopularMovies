package com.natashapetrenko.popularmovies;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.natashapetrenko.popularmovies.data.MovieContract;
import com.natashapetrenko.popularmovies.utilities.NetworkUtils;
import com.natashapetrenko.popularmovies.utilities.OpenMoviesJsonUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks,
        TrailerAdapter.OnItemClickListener,
        ReviewAdapter.OnReviewItemClickListener {

    private static final String TAG = "MovieDetailActivity";

    private static final int LOADER_ID = 10;
    private static final int REVIEW_LOADER_ID = 20;

    private int mMovieId;
    private boolean mFavorite = false;

    private TextView mTitleTextView;
    private ImageView mImageView;
    private TextView mPlotSynopsisTextView;
    private TextView mReleaseDateTextView;
    private TextView mUserRatingTextView;
    private ImageView mFavoriteImageView;

    private TrailerAdapter mTrailerAdapter;
    private RecyclerView mTrailerRecyclerView;

    private ReviewAdapter mReviewAdapter;
    private RecyclerView mReviewRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mTitleTextView = findViewById(R.id.tv_title);
        mImageView = findViewById(R.id.imgMovie);
        mReleaseDateTextView = findViewById(R.id.tv_release_date);
        mUserRatingTextView = findViewById(R.id.tv_user_rating);
        mPlotSynopsisTextView = findViewById(R.id.tv_plot_synopsis);
        mFavoriteImageView = findViewById(R.id.imgFavorite);

        mTrailerRecyclerView = findViewById(R.id.rv_trailers);
        mReviewRecyclerView = findViewById(R.id.rv_reviews);

        Intent intent = getIntent();

        mTitleTextView.setText(intent.getStringExtra("Title"));

        Picasso.with(this)
                .load(NetworkUtils.getImageURL(intent.getStringExtra("ImagePath")))
                .into(mImageView);

        mReleaseDateTextView.setText(intent.getStringExtra("ReleaseDate"));
        mUserRatingTextView.setText(intent.getStringExtra("UserRating"));
        mPlotSynopsisTextView.setText(intent.getStringExtra("PlotSynopsis"));

        mMovieId = intent.getIntExtra("Id", 0);

        mTrailerAdapter = new TrailerAdapter(this);
        mReviewAdapter = new ReviewAdapter(this);

        mTrailerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTrailerRecyclerView.setHasFixedSize(true);
        mTrailerRecyclerView.setAdapter(mTrailerAdapter);

        mReviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mReviewRecyclerView.setHasFixedSize(true);
        mReviewRecyclerView.setAdapter(mReviewAdapter);

        Bundle bundleTrailer = new Bundle();
        String sPathTrailers = String.format(NetworkUtils.TRAILERS_PATH, mMovieId);
        bundleTrailer.putString("url", sPathTrailers);
        bundleTrailer.putInt("id", LOADER_ID);

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader loader = loaderManager.getLoader(LOADER_ID);
        if (loader != null) {
            loaderManager.restartLoader(LOADER_ID, bundleTrailer, this);
        } else {
            loaderManager.initLoader(LOADER_ID, bundleTrailer, this);
        }

        Bundle bundleReview = new Bundle();
        String sPathReviews = String.format(NetworkUtils.REVIEWS_PATH, mMovieId);
        bundleReview.putString("url", sPathReviews);
        bundleReview.putInt("id", REVIEW_LOADER_ID);

        Loader reviewLoader = loaderManager.getLoader(REVIEW_LOADER_ID);
        if (reviewLoader != null) {
            loaderManager.restartLoader(REVIEW_LOADER_ID, bundleReview, this);
        } else {
            loaderManager.initLoader(REVIEW_LOADER_ID, bundleReview, this);
        }

        Cursor cursor = getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, null, "_id=?", new String[]{String.valueOf(mMovieId)}, null, null);
        mFavorite = cursor.getCount() > 0;
        cursor.close();

        setFavoriteIcon();
    }

    @Override
    public Loader onCreateLoader(int id, final Bundle args) {

        return new AsyncTaskLoader(this) {

            List<Trailer> trailerList = null;
            List<Review> reviewList = null;

            @Nullable
            @Override
            public Object loadInBackground() {

                String path = args.getString("url");
                URL requestUrl = NetworkUtils.buildUrl(path);

                try {
                    String jsonMoviesResponse = NetworkUtils
                            .getResponseFromHttpUrl(requestUrl);

                    Log.d(TAG, "doInBackground: " + jsonMoviesResponse);

                    if (args.getInt("id") == LOADER_ID) {
                        trailerList = OpenMoviesJsonUtils.getTralersFromJson(jsonMoviesResponse);
                    } else {
                        reviewList = OpenMoviesJsonUtils.getReviewsFromJson(jsonMoviesResponse);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }

                if (args.getInt("id") == LOADER_ID) {
                    return trailerList;
                } else {
                    return reviewList;
                }

            }

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if (trailerList != null) {
                    deliverResult(trailerList);
                } else {
                    forceLoad();
                }
            }

            @Override
            public void deliverResult(@Nullable Object data) {
                super.deliverResult(data);
            }
        };

    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

        if (data != null) {
            if (loader.getId() == LOADER_ID) {
                mTrailerAdapter.setTrailerList((List<Trailer>) data);
            } else {
                mReviewAdapter.setReviewList((List<Review>) data);
            }
            ;
        }

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    @Override
    public void onItemClick(String videoId) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoId));
        intent.putExtra("VIDEO_ID", videoId);
        startActivity(intent);
    }

    public void onClickFavorite(View view) {

        mFavorite = !mFavorite;
        if (mFavorite) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(MovieContract.MovieEntry._ID, mMovieId);
            contentValues.put(MovieContract.MovieEntry.COLUMN_TITLE, mTitleTextView.getText().toString());
            // Insert the content values via a ContentResolver
            Uri uri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);
        } else {
            Uri uri = MovieContract.MovieEntry.CONTENT_URI.buildUpon().appendPath(String.valueOf(mMovieId)).build();
            getContentResolver().delete(uri, null, null);
        }
        setFavoriteIcon();
    }

    private void setFavoriteIcon() {
        if (mFavorite) {
            mFavoriteImageView.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            mFavoriteImageView.setImageResource(android.R.drawable.btn_star_big_off);
        }
    }

    @Override
    public void onReviewItemClick(String id) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(id));
        startActivity(intent);
    }
}
