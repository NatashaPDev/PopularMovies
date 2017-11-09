package com.natashapetrenko.popularmovies;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by petrenkonv on 09.11.2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.TralerViewHolder> {

    private List<Review> mReviewList;
    private OnReviewItemClickListener mClickListener;

    public interface OnReviewItemClickListener {
        void onReviewItemClick(String id);
    }

    public ReviewAdapter(OnReviewItemClickListener clickListener) {
        mReviewList = new ArrayList<>();
        mClickListener = clickListener;
    }

    @Override
    public TralerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new TralerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TralerViewHolder holder, int position) {

        Review Review = mReviewList.get(position);
        holder.mReviewNameTextView.setText(Review.getContent());
        holder.itemView.setTag(Review.getUrl());

    }

    @Override
    public int getItemCount() {
        return mReviewList.size();
    }

    public class TralerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mReviewNameTextView;

        public TralerViewHolder(View itemView) {
            super(itemView);
            mReviewNameTextView = itemView.findViewById(R.id.tv_review);
            mReviewNameTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickListener.onReviewItemClick((String) itemView.getTag());
        }
    }

    public void setReviewList(List<Review> ReviewList) {
        mReviewList = ReviewList;
        notifyDataSetChanged();
    }
}
