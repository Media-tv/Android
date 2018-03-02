package com.vacuum.app.cinema.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.bluzwong.swipeback.SwipeBackActivityHelper;
import com.vacuum.app.cinema.Activities.detailsActivity;
import com.vacuum.app.cinema.Model.Movie;
import com.vacuum.app.cinema.R;

import java.util.List;

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
    public void onBindViewHolder(MovieViewHolder holder, final int position) {

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
                Activity activity = (Activity) mContext;
                Intent intent = new Intent(mContext.getApplicationContext(), detailsActivity.class);
                intent.putExtra("movie", movie);
               SwipeBackActivityHelper.activityBuilder(activity)
                        .intent(intent)
                        .needParallax(true)
                        .needBackgroundShadow(true)
                        .startActivity();

            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

}
