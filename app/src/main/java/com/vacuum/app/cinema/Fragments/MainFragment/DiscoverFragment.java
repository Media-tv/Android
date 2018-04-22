package com.vacuum.app.cinema.Fragments.MainFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vacuum.app.cinema.Adapter.MoviesAdapter;
import com.vacuum.app.cinema.Model.DoneMovies;
import com.vacuum.app.cinema.Model.Movie;
import com.vacuum.app.cinema.Model.MovieDetails;
import com.vacuum.app.cinema.Model.MoviesResponse;
import com.vacuum.app.cinema.R;
import com.vacuum.app.cinema.Utility.ApiClient;
import com.vacuum.app.cinema.Utility.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Home on 2/19/2018.
 */

public class DiscoverFragment extends Fragment {

    Context mContext;
    public static final String TAG_DISCVER_FRAGMENT = "TAG_DISCVER_FRAGMENT";
    RecyclerView discover_fragment_recylerview;
    private LinearLayoutManager mLayoutManager;
    List<Movie> movies = new ArrayList<>();

    MoviesAdapter moviesAdapter;
    String API_KEY;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.discoverfragment, container, false);
        mContext = this.getActivity();

        discover_fragment_recylerview=  view.findViewById(R.id.discover_fragment_recylerview);
        API_KEY = getString(R.string.TMBDB_API_KEY);

        retrofit();
        return view;
    }

    private void retrofit() {
        //====================================================================================
        //====================================================================================
        //====================================================================================

        ApiInterface apiService =
                ApiClient.getClient(mContext).create(ApiInterface.class);


        Call<DoneMovies> call = apiService.getDoneMovies("https://mohamedebrahim.000webhostapp.com/cimaclub/getDoneMovies.php");
        call.enqueue(new Callback<DoneMovies>() {
            @Override
            public void onResponse(Call<DoneMovies> call, Response<DoneMovies> response) {
                    for(Movie m : response.body().getMovie()){
                        setup_gridMovie(m.getId());
                    }
            }

            @Override
            public void onFailure(Call<DoneMovies> call, Throwable t) {
                Log.e("TAG", t.toString());
            }
        });
    }

    private void setup_gridMovie(int id) {

        ApiInterface apiService =
                ApiClient.getClient(mContext).create(ApiInterface.class);


        Call<MovieDetails> call = apiService.getMovieDetails(id,API_KEY);
        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {

                Movie e = new Movie();
                e.setTitle(response.body().getTitle());
                e.setPosterPath(response.body().getPosterPath());
                e.setVoteAverage(response.body().getVoteAverage());
                e.setReleaseDate(response.body().getReleaseDate());
                e.setOverview(response.body().getOverview());
                e.setId(response.body().getId());
                e.setVoteCount(response.body().getVoteCount());
                e.setVoteAverage(response.body().getVoteAverage());
                e.setBackdropPath(response.body().getBackdropPath());
                e.setOriginalTitle(response.body().getOriginalTitle());


                movies.add(e);
                mLayoutManager = new GridLayoutManager(mContext,3);
                discover_fragment_recylerview.setLayoutManager(mLayoutManager);
                moviesAdapter = new MoviesAdapter(movies, mContext);
                discover_fragment_recylerview.setAdapter(moviesAdapter);
            }
            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                Log.e("TAG", t.toString());
            }
        });
    }
}