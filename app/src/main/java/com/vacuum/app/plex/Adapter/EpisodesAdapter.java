package com.vacuum.app.plex.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vacuum.app.plex.Fragments.DetailsMovie_Fragment;
import com.vacuum.app.plex.Fragments.DetailsTV_Fragment;
import com.vacuum.app.plex.MainActivity;
import com.vacuum.app.plex.Model.Episode;
import com.vacuum.app.plex.Model.Movie;
import com.vacuum.app.plex.R;

import java.util.List;

/**
 * Created by Home on 3/3/2018.
 */

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.SearchViewHolder> {

    private List<Episode> episodes;
    private Context mContext;
    int tmdb_id,Season_number;

    static int position_item ;


    public  class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView Title;
        TextView year,overview;

        ImageView thumbnail,tv,play_eposides;
        ProgressBar progresssbar_watch_eposides;


        public SearchViewHolder(View v) {
            super(v);
            Title = (TextView) v.findViewById(R.id.Title);
            year = (TextView) v.findViewById(R.id.year);
            overview = (TextView) v.findViewById(R.id.overview);
            thumbnail = (ImageView) v.findViewById(R.id.thumbnail);
            tv = (ImageView) v.findViewById(R.id.tv);
            play_eposides = (ImageView) v.findViewById(R.id.play_eposides);
            progresssbar_watch_eposides = v.findViewById(R.id.progresssbar_watch_eposides);



        }
    }

    public EpisodesAdapter(List<Episode> episodes,int tmdb_id, int Season_number,Context mContext) {
        this.episodes = episodes;
        this.mContext = mContext;
        this.tmdb_id = tmdb_id;
        this.Season_number = Season_number;

    }

    @Override
    public EpisodesAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_search, parent, false);
        return new EpisodesAdapter.SearchViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final EpisodesAdapter.SearchViewHolder holder, final int position) {

        final Episode episode = episodes.get(position);
        holder.Title.setText(episode.getName());
        holder.year.setText(episode.getAirDate());
        holder.overview.setText(episode.getOverview());
        Glide.with(mContext).load("http://image.tmdb.org/t/p/w300" + episode.getStillPath()).into(holder.thumbnail);

        //onClick
        //==================================================================


        holder.play_eposides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position_item = position;
                holder.progresssbar_watch_eposides.setVisibility(View.VISIBLE);
                holder.play_eposides.setVisibility(View.GONE);
                openload(position+1);
                refreshBlockOverlay(holder,position_item);
            }

        });


    }
    private void openload(int EPISODE_NUMBER) {

        String url = "https://videospider.in/getvideo?key=Yz25qgFkgmtIjOfB&video_id="+tmdb_id+"&tmdb=1&tv=1&s="+Season_number+"&e="+EPISODE_NUMBER;
        Toast.makeText(mContext,  url, Toast.LENGTH_SHORT).show();
        Log.e("TAG",url);
    }


    @Override
    public int getItemCount() {
        return episodes.size();
    }


    private void Fragment(Movie movie,Fragment getfragment,String TAG) {
        Fragment fragment = getfragment;
        Bundle bundle = new Bundle();
        bundle.putSerializable("movie", movie);
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, fragment,TAG );
        fragmentTransaction.addToBackStack(MainActivity.CURRENT_TAG);
        fragmentTransaction.commit();
    }
    public void refreshBlockOverlay(EpisodesAdapter.SearchViewHolder holder,int position) {
        if(position != position_item)
        {
            holder.progresssbar_watch_eposides.setVisibility(View.GONE);
            holder.play_eposides.setVisibility(View.VISIBLE);
        }
        notifyItemChanged(position);
    }

}
