package com.vacuum.app.cinema.Adapter;

import android.content.Context;
import android.content.Intent;
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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vacuum.app.cinema.Activities.WatchActivity;
import com.vacuum.app.cinema.Fragments.DetailsMovie_Fragment;
import com.vacuum.app.cinema.Fragments.DetailsTV_Fragment;
import com.vacuum.app.cinema.MainActivity;
import com.vacuum.app.cinema.Model.Movie;
import com.vacuum.app.cinema.R;
import com.vacuum.app.cinema.Utility.ApiInterface;

import java.io.IOException;
import java.util.List;

import me.samthompson.bubbleactions.BubbleActions;
import me.samthompson.bubbleactions.Callback;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
        public MovieViewHolder(View v) {
            super(v);
            movieTitle = (TextView) v.findViewById(R.id.title);
            rating = (TextView) v.findViewById(R.id.rating);
            thumbnail = (ImageView) v.findViewById(R.id.thumbnail);
            ratingBar = (RatingBar) v.findViewById(R.id.ratingbar);

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

        Glide.with(mContext).load("http://image.tmdb.org/t/p/w185"+movies.get(position).getPosterPath()).into(holder.thumbnail);

        //onClick
        //==================================================================
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(movie.getTitle() == null){
                    Fragment(movie,new  DetailsTV_Fragment(),DetailsTV_Fragment.TAG_DetailsTV_Fragment);
                }else {
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
                                request_movie(movie.getId().toString(),movie.getTitle());
                            }
                        })
                        .addAction("Share", R.drawable.if_share4_216719, new Callback() {
                            @Override
                            public void doAction() {
                                Toast.makeText(view.getContext(), "Share pressed!", Toast.LENGTH_SHORT).show();
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

    private void request_movie(String id,String title) {
            String ROOT_URL = "https://mohamedebrahim.000webhostapp.com/";

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ROOT_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiInterface api = retrofit.create(ApiInterface.class);
            api.requestMovie(
                    id,
                    title
            ).enqueue(new retrofit2.Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if(response.isSuccessful()) {
                        String responsse ;
                        try {
                            responsse  = response.body().string();
                            System.out.println("====================================================");
                            System.out.println(responsse);
                            Toast.makeText(mContext,responsse, Toast.LENGTH_SHORT).show();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("TAG", "Unable to submit post to API.");
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
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, fragment,TAG );
        fragmentTransaction.addToBackStack(MainActivity.CURRENT_TAG);
        fragmentTransaction.commit();
    }

}
