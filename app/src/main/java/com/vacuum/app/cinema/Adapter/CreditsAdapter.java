package com.vacuum.app.cinema.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vacuum.app.cinema.Model.Cast;
import com.vacuum.app.cinema.Model.Trailer;
import com.vacuum.app.cinema.R;

import java.util.List;

/**
 * Created by Home on 3/1/2018.
 */

public class CreditsAdapter extends RecyclerView.Adapter<CreditsAdapter.CreditsViewHolder> {

    private List<Cast> casts;
    private Context mContext;


    public static class CreditsViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView character;
        ImageView thumbnail;
        public CreditsViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.trailerTitle);
            character = (TextView) v.findViewById(R.id.site);
            thumbnail = (ImageView) v.findViewById(R.id.thumbnail);

        }
    }

    public CreditsAdapter(List<Cast> casts, Context mContext) {
        this.casts = casts;
        this.mContext = mContext;
    }

    @Override
    public CreditsAdapter.CreditsViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_trailer, parent, false);
        return new CreditsAdapter.CreditsViewHolder(view);
    }
    @Override
    public void onBindViewHolder(CreditsAdapter.CreditsViewHolder holder, final int position) {

        holder.name.setText(casts.get(position).getName());
        holder.character.setText(casts.get(position).getCharacter());

        if (casts.get(position).getProfilePath()!= null)
        Glide.with(mContext).load("http://image.tmdb.org/t/p/w185"+casts.get(position).getProfilePath()).into(holder.thumbnail);


        //onClick
        //==================================================================

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return casts.size();
    }

}
