package com.natashapetrenko.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.natashapetrenko.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ForecastCursorAdapter extends RecyclerView.Adapter<ForecastCursorAdapter.MovieViewHolder> {

    private Cursor mCursor;
    private List<Movie> mMovieList;
    private final Context mContext;
    private final ListItemClickListener mClickListener;

    public ForecastCursorAdapter(Context context, ListItemClickListener clickListener) {
        mMovieList = new ArrayList<>();
        mContext = context;
        mClickListener = clickListener;
    }

    public void setMovieList(List<Movie> movieList) {
        mMovieList = movieList;
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        Movie movie = mMovieList.get(position);

        Picasso.with(mContext)
                .load(NetworkUtils.getImageURL(movie.getImagePath()))
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public interface ListItemClickListener {
        void onItemClick(Movie movie);
    }


    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView mImageView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imgMovie);
            mImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickListener.onItemClick(mMovieList.get(getAdapterPosition()));
        }
    }

}
