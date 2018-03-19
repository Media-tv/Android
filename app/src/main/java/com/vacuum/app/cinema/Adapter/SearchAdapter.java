package com.vacuum.app.cinema.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.bluzwong.swipeback.SwipeBackActivityHelper;
import com.vacuum.app.cinema.Activities.detailsActivity;
import com.vacuum.app.cinema.Model.Movie;
import com.vacuum.app.cinema.R;

import java.util.List;

/**
 * Created by Home on 3/3/2018.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private List<Movie> movies;
    private Context mContext;


    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView Title;
        TextView year,overview;
        ImageView thumbnail,tv;
        public SearchViewHolder(View v) {
            super(v);
            Title = (TextView) v.findViewById(R.id.Title);
            year = (TextView) v.findViewById(R.id.year);
            overview = (TextView) v.findViewById(R.id.overview);
            thumbnail = (ImageView) v.findViewById(R.id.thumbnail);
            tv = (ImageView) v.findViewById(R.id.tv);

        }
    }

    public SearchAdapter(List<Movie> movies, Context mContext) {
        this.movies = movies;
        this.mContext = mContext;
    }

    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_search, parent, false);
        return new SearchAdapter.SearchViewHolder(view);
    }


    @Override
    public void onBindViewHolder(SearchAdapter.SearchViewHolder holder, final int position) {

        final Movie movie = movies.get(position);


        if (movie.getTitle() == null) {
            holder.Title.setText(movie.getOriginal_name());
        } else {
            holder.Title.setText(movie.getTitle());
        }
        if (movie.getReleaseDate() != null) {
            holder.year.setText(movie.getReleaseDate());
        } else {
            holder.year.setText(movie.getFirst_air_date());
        }
        if (movie.getMedia_type().equals("tv")) {
            holder.tv.setVisibility(View.VISIBLE);
        }


        holder.overview.setText(movie.getOverview());

        Glide.with(mContext).load("http://image.tmdb.org/t/p/w185" + movies.get(position).getPosterPath()).into(holder.thumbnail);

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
