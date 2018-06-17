package com.vacuum.app.plex.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vacuum.app.plex.Fragments.YoutubeDialogFragment;
import com.vacuum.app.plex.Model.Trailer;
import com.vacuum.app.plex.R;

import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.vacuum.app.plex.Fragments.YoutubeDialogFragment.TAG_YOUTUBE_FRAGMENT;

/**
 * Created by Home on 2/24/2018.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private List<Trailer> trailer;

    private Context mContext;


    public static class TrailerViewHolder extends RecyclerView.ViewHolder {
        TextView trailerTitle;
        TextView site;
        ImageView thumbnail;
        public TrailerViewHolder(View v) {
            super(v);
            trailerTitle = (TextView) v.findViewById(R.id.trailerTitle);
            site = (TextView) v.findViewById(R.id.site);
            thumbnail = (ImageView) v.findViewById(R.id.thumbnail);

        }
    }

    public TrailerAdapter(List<Trailer> trailer, Context mContext) {
        this.trailer = trailer;
        this.mContext = mContext;
    }

    @Override
    public TrailerAdapter.TrailerViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_trailer, parent, false);
        return new TrailerAdapter.TrailerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(TrailerAdapter.TrailerViewHolder holder, final int position) {

        holder.trailerTitle.setText(trailer.get(position).getName());
        holder.site.setText(trailer.get(position).getSite());

        Glide.with(mContext)
                .load("https://img.youtube.com/vi/"+trailer.get(position).getKey()+"/hqdefault.jpg")
                .transition(withCrossFade())
                .into(holder.thumbnail);

        //onClick
        //==================================================================

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                YoutubeDialogFragment dialogFragment = new YoutubeDialogFragment ();
                Bundle bundle = new Bundle();
                bundle.putString("movie", trailer.get(position).getKey());
                dialogFragment.setArguments(bundle);
                FragmentTransaction fm = ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction();
                dialogFragment.show(fm, TAG_YOUTUBE_FRAGMENT);

                /*Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v="+trailer.get(position).getKey()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.google.android.youtube");
                mContext.startActivity(intent);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return trailer.size();
    }

}
