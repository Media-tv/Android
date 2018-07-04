package com.vacuum.app.plex.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.vacuum.app.plex.Activities.WatchActivity;
import com.vacuum.app.plex.Fragments.DetailsMovie_Fragment;
import com.vacuum.app.plex.Fragments.DetailsTV_Fragment;
import com.vacuum.app.plex.MainActivity;
import com.vacuum.app.plex.Model.Movie;
import com.vacuum.app.plex.R;
import com.vacuum.app.plex.Utility.ApiInterface;
import com.vacuum.app.plex.Utility.DownloadImage;
import com.vacuum.app.plex.Utility.RequestMovie;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Random;

import me.samthompson.bubbleactions.BubbleActions;
import me.samthompson.bubbleactions.Callback;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.vacuum.app.plex.Fragments.MainFragment.HomeFragment.mInterstitialAd;


/**
 * Created by Home on 2/20/2018.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<Movie> movies;
    private Context mContext;


    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView movieTitle;
        TextView rating;
        ImageView thumbnail;
        RatingBar ratingBar;
        ProgressBar vote_average_progressbar;
        public MovieViewHolder(View v) {
            super(v);
            movieTitle =  v.findViewById(R.id.title);
            rating =  v.findViewById(R.id.rating);
            thumbnail =  v.findViewById(R.id.thumbnail);
            ratingBar =  v.findViewById(R.id.ratingbar);
            vote_average_progressbar =  v.findViewById(R.id.vote_average_progressbar);

        }
    }

    public MoviesAdapter(List<Movie> movies, Context mContext) {
        this.movies = movies;
        this.mContext = mContext;

    }

    @Override
    public MoviesAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movie, parent, false);
        return new MovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final MovieViewHolder holder, final int position) {

        final Movie movie = movies.get(position);
        if(movie.getTitle()==null) {
            holder.movieTitle.setText(movie.getOriginal_name());
        }else {
            holder.movieTitle.setText(movie.getTitle());
        }
        holder.rating.setText(movie.getVoteAverage().toString());
        holder.ratingBar.setRating(movie.getVoteAverage().floatValue()/2);
        //=================================================
        holder.vote_average_progressbar.setProgress(movie.getVoteAverage().intValue()*10);
        Glide.with(mContext).load("http://image.tmdb.org/t/p/w342"+movies.get(position).getPosterPath()).into(holder.thumbnail);

        //onClick
        //==================================================================
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(movie.getTitle() == null){

                    Fragment(movie,new  DetailsTV_Fragment(),DetailsTV_Fragment.TAG_DetailsTV_Fragment);
                }else {

                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    } else {
                        //Toast.makeText(mContext, "The interstitial wasn't loaded yet.", Toast.LENGTH_SHORT).show();
                    }
                    Fragment(movie,new DetailsMovie_Fragment(),DetailsMovie_Fragment.TAG_DetailsMovie_Fragment);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                BubbleActions.on(view)
                        .addAction("Star", R.drawable.if_heart_119_111093, new Callback() {
                            @Override
                            public void doAction() {
                                //new RequestMovie(movie.getId().toString(),movie.getTitle()+" : "+movie.getReleaseDate().substring(0, 4),mContext);
                                Toast.makeText(view.getContext(), "Favourite!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addAction("Share", R.drawable.if_share4_216719, new Callback() {
                            @Override
                            public void doAction() {
                               // share(movie.getPosterPath());
                                new DownloadImage(mContext,movie.getPosterPath()).shareX();
                                Toast.makeText(view.getContext(), "Wait for share!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addAction("Hide", R.drawable.if_icon_close_round_211651, new Callback() {
                            @Override
                            public void doAction() {
                                Toast.makeText(view.getContext(), "Hide pressed!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                return false;
            }
        });
    }
    @Override
    public int getItemCount() {
        return movies.size();
    }
    private void Fragment(Movie movie,Fragment getfragment,String TAG) {
        Fragment fragment = getfragment;
        Bundle bundle = new Bundle();
        bundle.putSerializable("movie", movie);
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction();
        /*fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);*/
        fragmentTransaction.replace(R.id.frame, fragment,TAG );
        fragmentTransaction.addToBackStack(MainActivity.CURRENT_TAG);
        fragmentTransaction.commit();
    }

}
