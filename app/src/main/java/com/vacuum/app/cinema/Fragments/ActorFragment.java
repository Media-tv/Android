package com.vacuum.app.cinema.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.vacuum.app.cinema.Adapter.MoviesAdapter;
import com.vacuum.app.cinema.Model.Actor;
import com.vacuum.app.cinema.Model.ActorMovies;
import com.vacuum.app.cinema.Model.Movie;
import com.vacuum.app.cinema.R;
import com.vacuum.app.cinema.Utility.ApiInterface;
import com.vacuum.app.cinema.Utility.GetOpenload;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActorFragment extends Fragment {
    Context mContext;
    public  static final String TAG_ACTOR_FRAGMENT = "TAG_ACTOR_FRAGMENT";
    String ROOT_URL = "https://api.themoviedb.org/3/";
    String person_idd = "https://api.themoviedb.org/3/person/819?api_key=d08157e78b7478bea59e97af188b7054&language=en-US";
    String person_movies = "https://api.themoviedb.org/3/person/819/movie_credits?api_key=d08157e78b7478bea59e97af188b7054&language=en-US";

    int person_id;
    String TMBDB_API_KEY;
    TextView title,year,id_number_actor,place_of_birth_actor,biography;
    ImageView cover;
    RecyclerView recyclerView_movies_actor;
    FirebaseAnalytics mFirebaseAnalytics;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.actor_fragment, container, false);

        mContext = this.getContext();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(mContext);
        mFirebaseAnalytics.setCurrentScreen(getActivity(), TAG_ACTOR_FRAGMENT, null );

        title = view.findViewById(R.id.title);
        year = view.findViewById(R.id.year);
        id_number_actor = view.findViewById(R.id.id_number_actor);
        cover = view.findViewById(R.id.cover);
        place_of_birth_actor = view.findViewById(R.id.place_of_birth_actor);
        biography = view.findViewById(R.id.biography);
        recyclerView_movies_actor = view.findViewById(R.id.recyclerView_movies_actor);


        person_id = getArguments().getInt("person_id");

        Retrofit_call();
        return view;
    }


    private void Retrofit_call() {

        SharedPreferences prefs = mContext.getSharedPreferences("Plex", Activity.MODE_PRIVATE);
        TMBDB_API_KEY = prefs.getString("TMBDB_API_KEY",null);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);
        api.getActorDetails(person_id,TMBDB_API_KEY).enqueue(new retrofit2.Callback<Actor>() {
            @Override
            public void onResponse(Call<Actor> call, Response<Actor> response) {
                try{
                title.setText(response.body().getName());
                year.setText(response.body().getBirthday().substring(0,4));
                id_number_actor.setText("ID: "+response.body().getId());
                place_of_birth_actor.setText(response.body().getPlaceOfBirth());
                biography.setText(response.body().getBiography());

                Glide.with(mContext)
                        .load("http://image.tmdb.org/t/p/w780"+response.body().getProfilePath())
                        .into(cover);


                    Bundle bundle = new Bundle();
                    bundle.putString("actor", response.body().getName());
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);
                }catch (Exception e){
                    Log.e("TAG", "Unable to submit post to API.");
                }
            }
            @Override
            public void onFailure(Call<Actor> call, Throwable t) {
                Log.e("TAG", "Unable to submit post to API.");
            }
        });

        api.getActorMovies(person_id,TMBDB_API_KEY).enqueue(new retrofit2.Callback<ActorMovies>() {
            @Override
            public void onResponse(Call<ActorMovies> call, Response<ActorMovies> response) {
                try{
                    recyclerView_movies_actor.setLayoutManager(new GridLayoutManager(mContext,3,LinearLayoutManager.VERTICAL,true));
                    List<Movie> movies = response.body().getCast();
                    recyclerView_movies_actor.setAdapter(new MoviesAdapter(movies, mContext));
                    recyclerView_movies_actor.setNestedScrollingEnabled(false);

                }catch (Exception e){
                    Log.e("TAG", "Unable to submit post to API.");
                }
            }
            @Override
            public void onFailure(Call<ActorMovies> call, Throwable t) {
                Log.e("TAG", "Unable to submit post to API.");
            }
        });


    }




}
