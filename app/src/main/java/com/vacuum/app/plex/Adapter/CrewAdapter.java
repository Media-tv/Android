package com.vacuum.app.plex.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vacuum.app.plex.Model.Cast;
import com.vacuum.app.plex.Model.Crew;
import com.vacuum.app.plex.R;

import java.util.List;

/**
 * Created by Home on 3/1/2018.
 */

public class CrewAdapter extends RecyclerView.Adapter<CrewAdapter.CrewViewHolder> {

    private List<Crew> crews;
    private Context mContext;


    public static class CrewViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView character;
        ImageView thumbnail;
        public CrewViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            character = (TextView) v.findViewById(R.id.character);
            thumbnail = (ImageView) v.findViewById(R.id.thumbnail);

        }
    }

    public CrewAdapter(List<Crew> crews, Context mContext) {
        this.crews = crews;
        this.mContext = mContext;
    }

    @Override
    public CrewAdapter.CrewViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cast, parent, false);
        return new CrewAdapter.CrewViewHolder(view);
    }
    @Override
    public void onBindViewHolder(CrewAdapter.CrewViewHolder holder, final int position) {

        holder.name.setText(crews.get(position).getName());
        holder.character.setText(crews.get(position).getDepartment());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.if_person);
        requestOptions.error(R.drawable.if_person);
        Glide.with(mContext).setDefaultRequestOptions(requestOptions).load("http://image.tmdb.org/t/p/w185"+crews.get(position).getProfilePath()).apply(RequestOptions.circleCropTransform()).into(holder.thumbnail);


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
        return crews.size();
    }

}
