package com.vacuum.app.cinema.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vacuum.app.cinema.Model.Poster;
import com.vacuum.app.cinema.Model.Trailer;
import com.vacuum.app.cinema.R;

import java.util.List;

/**
 * Created by Home on 2/24/2018.
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.TrailerViewHolder> {

    private List<Poster> posters;

    private Context mContext;


    public static class TrailerViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        public TrailerViewHolder(View v) {
            super(v);
            thumbnail = (ImageView) v.findViewById(R.id.thumbnail);

        }
    }

    public ImagesAdapter(List<Poster> posters, Context mContext) {
        this.posters = posters;
        this.mContext = mContext;
    }

    @Override
    public ImagesAdapter.TrailerViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_images, parent, false);
        return new ImagesAdapter.TrailerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ImagesAdapter.TrailerViewHolder holder, final int position) {

        Glide.with(mContext).load("http://image.tmdb.org/t/p/w500"+posters.get(position).getFilePath()).into(holder.thumbnail);

        //onClick
        //==================================================================

    }

    @Override
    public int getItemCount() {
        return posters.size();
    }

}
