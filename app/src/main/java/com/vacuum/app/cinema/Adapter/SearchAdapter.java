package com.vacuum.app.cinema.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vacuum.app.cinema.Fragments.DetailsMovie_Fragment;
import com.vacuum.app.cinema.Fragments.DetailsTV_Fragment;
import com.vacuum.app.cinema.MainActivity;
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
            holder.Title.setText(movie.getOriginalTitle());
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

        Glide.with(mContext).load("http://image.tmdb.org/t/p/w500" + movies.get(position).getPosterPath()).into(holder.thumbnail);

        //onClick
        //==================================================================

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(movie.getTitle() == null){
                    Fragment(movie,new DetailsTV_Fragment(),DetailsTV_Fragment.TAG_DetailsTV_Fragment);
                }else {
                    Fragment(movie,new DetailsMovie_Fragment(),DetailsMovie_Fragment.TAG_DetailsMovie_Fragment);
                }
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
