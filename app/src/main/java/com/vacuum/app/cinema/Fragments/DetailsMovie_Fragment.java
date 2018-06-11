package com.vacuum.app.cinema.Fragments;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.vacuum.app.cinema.Activities.WatchActivity;
import com.vacuum.app.cinema.Adapter.CreditsAdapter;
import com.vacuum.app.cinema.Adapter.CrewAdapter;
import com.vacuum.app.cinema.Adapter.ImagesAdapter;
import com.vacuum.app.cinema.Adapter.TrailerAdapter;
import com.vacuum.app.cinema.Model.Backdrop;
import com.vacuum.app.cinema.Model.Cast;
import com.vacuum.app.cinema.Model.Credits;
import com.vacuum.app.cinema.Model.Crew;
import com.vacuum.app.cinema.Model.Genre;
import com.vacuum.app.cinema.Model.Images_tmdb;
import com.vacuum.app.cinema.Model.Movie;
import com.vacuum.app.cinema.Model.MovieDetails;
import com.vacuum.app.cinema.Model.Poster;
import com.vacuum.app.cinema.Model.Trailer;
import com.vacuum.app.cinema.Model.TrailerResponse;
import com.vacuum.app.cinema.R;
import com.vacuum.app.cinema.Utility.ApiClient;
import com.vacuum.app.cinema.Utility.ApiInterface;
import com.vacuum.app.cinema.Utility.GetOpenload;
import com.vacuum.app.cinema.Utility.RequestMovie;
import com.vacuum.app.cinema.Utility.UploadOpenload;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by Home on 3/3/2018.
 */

public class DetailsMovie_Fragment extends Fragment implements View.OnClickListener{

    private static boolean LIKE = true;
    public static final String TAG_DetailsMovie_Fragment = "TAG_DetailsMovie_Fragment";

    TextView title,year,rating,overview,runtime,voteCount,genre1,genre2,genre3,id_number;
    ImageView cover;
    Context mContext;
    ProgressBar progresssbar_watch;
    RecyclerView recyclerView_trailers,recyclerView_actors,recyclerView_crew,recyclerView_images;
    RelativeLayout layout_genre1,layout_genre2,layout_genre3;
    LinearLayout trailer_layout,actors_layout,crew_layout,image_layout;
    RatingBar ratingBar;
    ImageView like,watch;
    int x;
    Movie movie;
    Handler mHandler;
    Runnable myRunnable;
    ApiInterface apiService;
    String TMBDB_API_KEY,gener1_analistc;
    AdView adView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.details_movie_fragment_layout, container, false);

        mContext = this.getActivity();

        title = view.findViewById(R.id.title);
        year = view.findViewById(R.id.year);
        rating = view.findViewById(R.id.rating);
        overview = view.findViewById(R.id.overview);
        runtime = view.findViewById(R.id.runtime);
        cover = view.findViewById(R.id.cover);
        ratingBar = view.findViewById(R.id.ratingBar);
        voteCount = view.findViewById(R.id.voteCount);
        genre1 = view.findViewById(R.id.genre1);
        genre2 = view.findViewById(R.id.genre2);
        genre3 = view.findViewById(R.id.genre3);
        id_number = view.findViewById(R.id.id_number);
        layout_genre1 = view.findViewById(R.id.layout_genre1);
        layout_genre2 = view.findViewById(R.id.layout_genre2);
        layout_genre3 = view.findViewById(R.id.layout_genre3);
        like = view.findViewById(R.id.like);
        watch = view.findViewById(R.id.watch);
        trailer_layout = view.findViewById(R.id.trailer_layout);
        actors_layout = view.findViewById(R.id.actors_layout);
        image_layout = view.findViewById(R.id.image_layout);
        progresssbar_watch = view.findViewById(R.id.progresssbar_watch);

        recyclerView_trailers = view.findViewById(R.id.recyclerView_trailers);
        recyclerView_actors = view.findViewById(R.id.recyclerView_actors);
        recyclerView_images = view.findViewById(R.id.recyclerView_images);
        recyclerView_crew = view.findViewById(R.id.recyclerView_crew);
        crew_layout = view.findViewById(R.id.crew_layout);

        adView = view.findViewById(R.id.adView_details_fragment);

        like.setOnClickListener(this);
        watch.setOnClickListener(this);


        SharedPreferences prefs = mContext.getSharedPreferences("Plex", Activity.MODE_PRIVATE);
        TMBDB_API_KEY = prefs.getString("TMBDB_API_KEY",null);


        movie = (Movie)getArguments().getSerializable("movie");



        try{
            title.setText(movie.getOriginalTitle());
            year.setText(movie.getReleaseDate().substring(0, 4));
            rating.setText(movie.getVoteAverage().toString());
            overview.setText(movie.getOverview());
            ratingBar.setRating(movie.getVoteAverage().floatValue()/2);
            voteCount.setText(movie.getVoteCount().toString());
            x= movie.getId();
            id_number.setText("ID:" + String.valueOf(x));
            String cover_string_link ;
            if(movie.getBackdropPath() == null){
                cover_string_link = "http://image.tmdb.org/t/p/w780"+movie.getPosterPath().toString();
            }else {
                cover_string_link = "http://image.tmdb.org/t/p/w780"+movie.getBackdropPath().toString();
            }
            Glide.with(this)
                    .load(cover_string_link)
                    .transition(withCrossFade())
                    .into(cover);
        }catch (Exception e){
            Log.i("TAG :",e.toString());
        }
        retrofit();
        Ads();
        return view;
    }
    private void analistcs() {
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(mContext);
        mFirebaseAnalytics.setCurrentScreen(getActivity(), "DetailsMovie_Fragment", null );
    }

    private void retrofit() {

        apiService =
                ApiClient.getClient(mContext).create(ApiInterface.class);

        Call<MovieDetails> call_details = apiService.getMovieDetails(x,TMBDB_API_KEY);
        call_details.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                MovieDetails m = response.body();


                    try{
                        int t = m.getRuntime();
                        int hours = t / 60; //since both are ints, you get an int
                        int minutes = t % 60;
                        System.out.printf("%d:%02d", hours, minutes);
                        if(hours != 0)
                            runtime.setText(String.valueOf(hours)+"h "+String.valueOf(minutes)+"min");
                        else
                            runtime.setText(String.valueOf(minutes)+"min");

                        setgenre(m.getGenres(),m.getGenres().size());
                    }catch (Exception e){
                        Log.i("TAG :",e.toString());
                    }

                //============================++++++++++++++++++++++++++

            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                // Log error here since request failed
                Log.e("TAG", t.toString());
            }
        });

        //setRecyclerView_trailers
        //==================================================================
        Call<TrailerResponse> call_RecyclerView_trailers = apiService.gettrailers("movie",x,TMBDB_API_KEY);
        call_RecyclerView_trailers.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                List<Trailer> trailers = null;
                try {
                    trailers = response.body().getResults();
                    if (response.body().getResults() != null)
                    {
                        trailer_layout.setVisibility(View.VISIBLE);
                        recyclerView_trailers.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                        recyclerView_trailers.setAdapter(new TrailerAdapter(trailers, mContext));


                    }
                }catch (Exception e ){
                    Log.e("Exception::",e.toString());
                }
            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("TAG", t.toString());
            }
        });

        //setRecyclerView_actors
        //==================================================================
        Call<Credits> call_recyclerView_actors = apiService.getMovieCredits(x,TMBDB_API_KEY);
        call_recyclerView_actors.enqueue(new Callback<Credits>() {
            @Override
            public void onResponse(Call<Credits> call, Response<Credits> response) {
                List<Cast> casts = null;
                try {
                    casts = response.body().getCast();
                    if (casts != null)
                    {
                        actors_layout.setVisibility(View.VISIBLE);
                        recyclerView_actors.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                        recyclerView_actors.setAdapter(new CreditsAdapter(casts, mContext));

                    }
                }catch (Exception e ){
                    Log.e("Exception::>>",e.toString());
                }
            }

            @Override
            public void onFailure(Call<Credits> call, Throwable t) {
                // Log error here since request failed
                Log.e("tag", t.toString());
            }
        });


        //setRecyclerView_actors
        //==================================================================
        Call<Credits> call_recyclerView_crew = apiService.getMovieCredits(x,TMBDB_API_KEY);
        call_recyclerView_crew.enqueue(new Callback<Credits>() {
            @Override
            public void onResponse(Call<Credits> call, Response<Credits> response) {
                List<Crew> crews = null;
                try {
                    crews = response.body().getCrew();
                    if (response.body().getCrew() != null)
                    {
                        crew_layout.setVisibility(View.VISIBLE);
                        recyclerView_crew.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                        recyclerView_crew.setAdapter(new CrewAdapter(crews, mContext));

                    }
                }catch (Exception e ){
                    Log.e("Exception::",e.toString());
                }
            }

            @Override
            public void onFailure(Call<Credits> call, Throwable t) {
                // Log error here since request failed
                Log.e("tag", t.toString());
            }
        });

        call_Retrofit_images();
    }
    private void Ads() {
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }
    private void call_Retrofit_images() {
        //setRecyclerView_images
        //==================================================================
        Call<Images_tmdb> call_RecyclerView_images = apiService.getImages("movie",x,TMBDB_API_KEY);
        call_RecyclerView_images.enqueue(new Callback<Images_tmdb>() {
            @Override
            public void onResponse(Call<Images_tmdb> call, Response<Images_tmdb> response) {
                //List<Poster> posters;
                List<Backdrop> posters;
                posters = response.body().getBackdrops();
                image_layout.setVisibility(View.VISIBLE);
                recyclerView_images.setLayoutManager(new LinearLayoutManager(mContext,
                        LinearLayoutManager.HORIZONTAL, false));
                recyclerView_images.setAdapter(new ImagesAdapter(posters, mContext));
                if(posters.size()>0){change_backdrop(posters);}

            }

            @Override
            public void onFailure(Call<Images_tmdb> call, Throwable t) {
                // Log error here since request failed
                Log.e("TAG", t.toString());
            }
        });
    }


    private void change_backdrop(final List<Backdrop> imageArray) {
        mHandler=  new Handler();
        myRunnable = new Runnable() {
            int i = 0;

            public void run() {
                Glide.with(mContext)
                        .load("http://image.tmdb.org/t/p/w780"+imageArray.get(i).getFilePath())
                        .transition(withCrossFade())
                        .apply(new RequestOptions().placeholder(cover.getDrawable()))
                        .into(cover);
                Log.e("TAG","change image bacrfull");
                i++;
                if (i > imageArray.size() - 1) {
                    i = 0;
                }
                mHandler.postDelayed(this, 4000);
            }
        };
        mHandler.postDelayed(myRunnable, 2000);
    }

    public void setgenre(List<Genre> names, int size){

        //Log.e("size",String.valueOf(size));

        if (size==1){
            genre1.setText(String.valueOf(names.get(0).getName()));
            layout_genre1.setVisibility(View.VISIBLE);
            gener1_analistc = names.get(0).getName();
            analistcs();

        }else if(size ==2){
            genre1.setText(String.valueOf(names.get(0).getName()));
            layout_genre1.setVisibility(View.VISIBLE);
            genre2.setText(String.valueOf(names.get(1).getName()));
            layout_genre2.setVisibility(View.VISIBLE);

        }else if(size >= 3) {
            genre1.setText(String.valueOf(names.get(0).getName()));
            layout_genre1.setVisibility(View.VISIBLE);
            genre2.setText(String.valueOf(names.get(1).getName()));
            layout_genre2.setVisibility(View.VISIBLE);
            genre3.setText(String.valueOf(names.get(2).getName()));
            layout_genre3.setVisibility(View.VISIBLE);
        }


    }





    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.like:
                if (LIKE){
                    like.setImageResource(R.drawable.if_heart_119_111093);
                    LIKE = false;
                }else {
                    like.setImageResource(R.drawable.if_heart_1814104);
                    LIKE = true;
                }
                    break;
            case  R.id.watch:
                retrofit_getfile_openload_id();
                break;
        }
    }

    private void retrofit_getfile_openload_id() {
        progresssbar_watch.setVisibility(View.VISIBLE);
        watch.setVisibility(View.GONE);

        apiService =ApiClient.getClient(mContext).create(ApiInterface.class);

        String url = "https://mohamedebrahim.000webhostapp.com/cimaclub/getfileid.php?id="+x ;
        Call<String> call_details = apiService.getMovie_openload_id(url);
        call_details.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String m = response.body();
                if(m == null){
                    new UploadOpenload(mContext,movie.getId().toString(),movie.getTitle()+" : "+movie.getReleaseDate().substring(0, 4));
                    progresssbar_watch.setVisibility(View.GONE);
                    watch.setVisibility(View.VISIBLE);
                    Toast.makeText(mContext,"Go Premium!", Toast.LENGTH_SHORT).show();
                    Vibrator v = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        v.vibrate(VibrationEffect.createOneShot(100,VibrationEffect.DEFAULT_AMPLITUDE));
                    }else{
                        v.vibrate(100);
                    }
                    //===============================================
                }else {
                    new GetOpenload(mContext,m.toString(),movie.getOriginalTitle());
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Log error here since request failed
                Log.e("tag", t.toString());
            }
        });

    }




    @Override
    public void onPause() {
        super.onPause();
        Log.e("TAG","Stop handler ");
        if(myRunnable != null){
            mHandler.removeCallbacksAndMessages(null);
            mHandler.removeCallbacks(myRunnable);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        progresssbar_watch.setVisibility(View.GONE);
        watch.setVisibility(View.VISIBLE);
        call_Retrofit_images();
    }
}
