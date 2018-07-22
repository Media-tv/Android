package com.vacuum.app.plex.Adapter;

import android.app.Dialog;
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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vacuum.app.plex.Fragments.DetailsMovie_Fragment;
import com.vacuum.app.plex.Fragments.DetailsTV_Fragment;
import com.vacuum.app.plex.MainActivity;
import com.vacuum.app.plex.Model.Episode;
import com.vacuum.app.plex.Model.Link;
import com.vacuum.app.plex.Model.Movie;
import com.vacuum.app.plex.R;
import com.vacuum.app.plex.Utility.Balance;
import com.vacuum.app.plex.Utility.UploadOpenload;

import java.util.List;

/**
 * Created by Home on 3/3/2018.
 */

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.SearchViewHolder> {

    private List<Episode> episodes;
    private Context mContext;
    private Link l;
    private int mCurrentPlayingPosition = -1;

    public  class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView Title;
        TextView year,overview;

        ImageView thumbnail,tv,play_eposides,download_tv_icon;
        ProgressBar progresssbar_watch_eposides,progresssbar_download_tv;


        public SearchViewHolder(View v) {
            super(v);
            Title =  v.findViewById(R.id.Title);
            year =  v.findViewById(R.id.year);
            overview =  v.findViewById(R.id.overview);
            thumbnail =  v.findViewById(R.id.thumbnail);
            tv =  v.findViewById(R.id.tv);
            play_eposides =  v.findViewById(R.id.play_eposides);
            download_tv_icon = v.findViewById(R.id.download_tv_icon);

            progresssbar_watch_eposides = v.findViewById(R.id.progresssbar_watch_eposides);
            progresssbar_download_tv = v.findViewById(R.id.progresssbar_download_tv);



        }
    }

    public EpisodesAdapter(List<Episode> episodes,Link l,Context mContext) {
        this.episodes = episodes;
        this.mContext = mContext;
        this.l = l;

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
        if(mCurrentPlayingPosition == position ){
            holder.progresssbar_watch_eposides.setVisibility(View.VISIBLE);
            holder.play_eposides.setVisibility(View.GONE);
            // display stop icon
        } else {
            holder.progresssbar_watch_eposides.setVisibility(View.GONE);
            holder.play_eposides.setVisibility(View.VISIBLE);
            // display play icon
        }


        holder.play_eposides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.progresssbar_watch_eposides.setVisibility(View.VISIBLE);
                holder.play_eposides.setVisibility(View.GONE);
                if(new Balance(mContext, Integer.parseInt(episode.getAirDate().substring(0, 4))).calculate_points() == true){
                    notRobotCapcha(position);
                    holder.progresssbar_watch_eposides.setVisibility(View.VISIBLE);
                    holder.play_eposides.setVisibility(View.GONE);
                }else {
                    holder.progresssbar_watch_eposides.setVisibility(View.GONE);
                    holder.play_eposides.setVisibility(View.VISIBLE);
                    Toast.makeText(mContext, "not enough money", Toast.LENGTH_SHORT).show();
                }
                int PlayStopButtonState = (holder.play_eposides.getTag() == null) ? 1 : (Integer) holder.play_eposides.getTag();
                int previousPosition = mCurrentPlayingPosition;
                if (PlayStopButtonState == 1) {
                    mCurrentPlayingPosition = position;
                    holder.play_eposides.setTag(2);
                } else { mCurrentPlayingPosition = -1; // nothing wil be played after hitting stop
                    holder.play_eposides.setTag(1); }
                if(previousPosition != -1)
                    notifyItemChanged(previousPosition);
            }
        });
        holder.download_tv_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.progresssbar_download_tv.setVisibility(View.VISIBLE);
                holder.download_tv_icon.setVisibility(View.GONE);
                Toast.makeText(mContext, "Go Premium!", Toast.LENGTH_SHORT).show();
                //notRobotCapcha(position);
                int PlayStopButtonState = (holder.download_tv_icon.getTag() == null) ? 1 : (Integer) holder.download_tv_icon.getTag();
                int previousPosition = mCurrentPlayingPosition;
                if (PlayStopButtonState == 1) {
                    mCurrentPlayingPosition = position;
                    holder.download_tv_icon.setTag(2);
                } else { mCurrentPlayingPosition = -1; // nothing wil be played after hitting stop
                    holder.download_tv_icon.setTag(1); }
                if(previousPosition != -1)
                    notifyItemChanged(previousPosition);
            }
        });
    }
    private void notRobotCapcha(final int Position) {
        WebView webView = null;
        Log.e("TAG","Eposid:"+Position);
        String cv = "https://videospider.in/getvideo?key=Yz25qgFkgmtIjOfB&video_id="+l.getId_tvseries_tmdb()+"&tmdb=1&tv=1&s="+l.getSeason_number()+"&e="+(Position+1);
        final Dialog dialoge = new Dialog(mContext);
        dialoge.setContentView(R.layout.robotcapcha);
        webView = (WebView) dialoge.findViewById(R.id.webview2);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(cv);
        webView.setWebViewClient(new WebViewClient() {
                                     @Override
                                     public boolean shouldOverrideUrlLoading(WebView view, String openload_url) {
                                         Log.e("TAG", openload_url);
                                         String word = ".co";
                                         String full_url = "https://openloed.co/embed/RoZzZ3TcXQ0";
                                         int index = openload_url.lastIndexOf(word); //16
                                         if(index != -1){
                                             Log.e("TAG: index == ", String.valueOf(index));
                                             if (index != 0) {
                                                 String right_url = "https://openload" + openload_url.substring(index, openload_url.length());
                                                 openload(Position, right_url);
                                             }
                                             dialoge.dismiss();
                                         }else {
                                             Toast.makeText(mContext, "Bad connection", Toast.LENGTH_SHORT).show();
                                             dialoge.dismiss();
                                         }
                                         return true;
                                     }
                                 }
        );
        dialoge.show();
    }
    private void openload(int Position,String right_url) {
        Link l2 = new Link();
        l2.setUrl(right_url);
        l2.setId_tvseries_tmdb(l.getId_tvseries_tmdb());
        l2.setName_tv_series(l.getName_tv_series());
        l2.setSeason_number(l.getSeason_number());
        l2.setEpisode_number(episodes.get(Position).getEpisodeNumber());
        l2.setEpisode_id_tmdb(episodes.get(Position).getId());
        l2.setEpisode_name(episodes.get(Position).getName());
        new UploadOpenload(mContext,l2);
    }
    @Override
    public int getItemCount() {
        return episodes.size();
    }
}
