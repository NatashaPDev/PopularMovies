package com.natashapetrenko.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.natashapetrenko.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    private TextView mTitleTextView;
    private ImageView mImageView;
    private TextView mPlotSynopsisTextView;
    private TextView mReleaseDateTextView;
    private TextView mUserRatingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mTitleTextView = findViewById(R.id.tv_title);
        mImageView = findViewById(R.id.imgMovie);
        mReleaseDateTextView = findViewById(R.id.tv_release_date);
        mUserRatingTextView = findViewById(R.id.tv_user_rating);
        mPlotSynopsisTextView = findViewById(R.id.tv_plot_synopsis);

        Intent intent = getIntent();

        mTitleTextView.setText(intent.getStringExtra("Title"));

        Picasso.with(this)
                .load(NetworkUtils.getImageURL(intent.getStringExtra("ImagePath")))
                .into(mImageView);

        mReleaseDateTextView.setText(intent.getStringExtra("ReleaseDate"));
        mUserRatingTextView.setText(intent.getStringExtra("UserRating"));
        mPlotSynopsisTextView.setText(intent.getStringExtra("PlotSynopsis"));

    }
}
