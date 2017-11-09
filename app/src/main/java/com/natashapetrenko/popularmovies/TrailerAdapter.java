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

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TralerViewHolder> {

    private List<Trailer> mTrailerList;
    private OnItemClickListener mClickListener;

    public interface OnItemClickListener {
        void onItemClick(String id);
    }

    public TrailerAdapter(OnItemClickListener clickListener) {
        mTrailerList = new ArrayList<>();
        mClickListener = clickListener;
    }

    @Override
    public TralerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item, parent, false);
        return new TralerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TralerViewHolder holder, int position) {

        Trailer trailer = mTrailerList.get(position);
        holder.mTrailerNameTextView.setText(trailer.getName());
        holder.itemView.setTag(trailer.getId());

    }

    @Override
    public int getItemCount() {
        return mTrailerList.size();
    }

    public class TralerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTrailerNameTextView;

        public TralerViewHolder(View itemView) {
            super(itemView);
            mTrailerNameTextView = itemView.findViewById(R.id.tv_trailer_name);
            mTrailerNameTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickListener.onItemClick((String) itemView.getTag());
        }
    }

    public void setTrailerList(List<Trailer> trailerList) {
        mTrailerList = trailerList;
        notifyDataSetChanged();
    }
}
