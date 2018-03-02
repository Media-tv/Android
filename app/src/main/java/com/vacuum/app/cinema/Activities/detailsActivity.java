package com.vacuum.app.cinema.Activities;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.bluzwong.swipeback.SwipeBackActivityHelper;
import com.vacuum.app.cinema.Adapter.CreditsAdapter;
import com.vacuum.app.cinema.Adapter.MoviesAdapter;
import com.vacuum.app.cinema.Adapter.TrailerAdapter;
import com.vacuum.app.cinema.Model.Cast;
import com.vacuum.app.cinema.Model.Credits;
import com.vacuum.app.cinema.Model.Crew;
import com.vacuum.app.cinema.Model.Genre;
import com.vacuum.app.cinema.Model.Movie;
import com.vacuum.app.cinema.Model.MovieDetails;
import com.vacuum.app.cinema.Model.MoviesResponse;
import com.vacuum.app.cinema.Model.Trailer;
import com.vacuum.app.cinema.Model.TrailerResponse;
import com.vacuum.app.cinema.R;
import com.vacuum.app.cinema.Utility.ApiClient;
import com.vacuum.app.cinema.Utility.ApiInterface;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Home on 2/22/2018.
 */



public class detailsActivity  extends AppCompatActivity {

    private final static String API_KEY = "";
    private static boolean LIKE = true;

    SwipeBackActivityHelper helper = new SwipeBackActivityHelper();
    TextView title,year,rating,overview,runtime,voteCount,genre1,genre2,genre3;
    ImageView cover;
    Context mContext;
    RecyclerView recyclerView_trailers,recyclerView_actors,recyclerView_crew;
    LinearLayoutManager mLayoutManager ;
    RelativeLayout layout_genre1,layout_genre2,layout_genre3;
    LinearLayout trailer_layout;
    RatingBar ratingBar;
    ImageView like;
    int x;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity_layout);
        mContext = this.getApplicationContext();
        helper.setEdgeMode(false)
                .setParallaxMode(true)
                .setParallaxRatio(3)
                .setNeedBackgroundShadow(true)
                .init(this);

        title = findViewById(R.id.title);
        year = findViewById(R.id.year);
        rating = findViewById(R.id.rating);
        overview = findViewById(R.id.overview);
        runtime = findViewById(R.id.runtime);
        cover = findViewById(R.id.cover);
        ratingBar = findViewById(R.id.ratingBar);
        voteCount = findViewById(R.id.voteCount);
        genre1 = findViewById(R.id.genre1);
        genre2 = findViewById(R.id.genre2);
        genre3 = findViewById(R.id.genre3);
        layout_genre1 = findViewById(R.id.layout_genre1);
        layout_genre2 = findViewById(R.id.layout_genre2);
        layout_genre3 = findViewById(R.id.layout_genre3);
        like = findViewById(R.id.like);
        trailer_layout = findViewById(R.id.trailer_layout);

        recyclerView_trailers = findViewById(R.id.recyclerView_trailers);
        recyclerView_actors = findViewById(R.id.recyclerView_actors);
        recyclerView_crew = findViewById(R.id.recyclerView_crew);
        mLayoutManager= new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);


        //Bundle data = getIntent().getExtras();
        //Movie movie = (Movie) data.getParcelable("Movie");

        Movie movie = (Movie) getIntent().getSerializableExtra("movie");

        title.setText(movie.getTitle());
        if(movie.getReleaseDate()!=null) {
            year.setText(movie.getReleaseDate().substring(0, 4));
        }
        rating.setText(movie.getVoteAverage().toString());
        overview.setText(movie.getOverview());
        ratingBar.setRating(movie.getVoteAverage().floatValue()/2);
        voteCount.setText(movie.getVoteCount().toString());

        x= movie.getId();

        Glide.with(this)
                .load("http://image.tmdb.org/t/p/w500"+movie.getBackdropPath().toString())
                .into(cover);
        retrofit();
        recyclerViewTouch();
        Like();
    }




    private void retrofit() {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<MovieDetails> call_UpComing = apiService.getMovieDetails(x,API_KEY);
        call_UpComing.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                MovieDetails m = response.body();


                if(m.getRuntime() != 0){
                    int t = m.getRuntime();
                    int hours = t / 60; //since both are ints, you get an int
                    int minutes = t % 60;
                    System.out.printf("%d:%02d", hours, minutes);
                    runtime.setText(String.valueOf(hours)+"h "+String.valueOf(minutes)+"min");

                }

                //============================++++++++++++++++++++++++++

                setgenre(m.getGenres(),m.getGenres().size());

            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                // Log error here since request failed
                Log.e("tag", t.toString());
            }
        });

        //setRecyclerView_trailers
        //==================================================================
        Call<TrailerResponse> call_RecyclerView_trailers = apiService.getMovietrailers(x,API_KEY);
        call_RecyclerView_trailers.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                List<Trailer> trailers = null;
                try {
                    trailers = response.body().getResults();
                    if (response.body().getResults() != null)
                    {
                        trailer_layout.setVisibility(View.VISIBLE);
                        recyclerView_trailers.setLayoutManager(mLayoutManager);
                        recyclerView_trailers.setAdapter(new TrailerAdapter(trailers, mContext));
                    }
                }catch (Exception e ){
                    Log.e("Exception::",e.toString());
                }
            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("tag", t.toString());
            }
        });

        //setRecyclerView_actors
        //==================================================================
        Call<Credits> call_recyclerView_actors = apiService.getMovieCredits(x,API_KEY);
        call_recyclerView_actors.enqueue(new Callback<Credits>() {
            @Override
            public void onResponse(Call<Credits> call, Response<Credits> response) {
                List<Cast> casts = null;
                try {
                    casts = response.body().getCast();
                    if (response.body().getCast() != null)
                    {
                        //trailer_layout.setVisibility(View.VISIBLE);
                        recyclerView_actors.setLayoutManager(mLayoutManager);
                        recyclerView_actors.setAdapter(new CreditsAdapter(casts, mContext));
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


        //setRecyclerView_actors
        //==================================================================
        Call<Credits> call_recyclerView_crew = apiService.getMovieCredits(x,API_KEY);
        call_recyclerView_crew.enqueue(new Callback<Credits>() {
            @Override
            public void onResponse(Call<Credits> call, Response<Credits> response) {
                List<Crew> crews = null;
                try {
                    crews = response.body().getCrew();
                    if (response.body().getCast() != null)
                    {
                        //trailer_layout.setVisibility(View.VISIBLE);
                        //recyclerView_crew.setLayoutManager(mLayoutManager);
                        //recyclerView_crew.setAdapter(new CreditsAdapter(crews, mContext));
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

    }

    public void setgenre(List<Genre> names, int size){

        //Log.e("size",String.valueOf(size));

        if (size==1){
            genre1.setText(String.valueOf(names.get(0).getName()));
            layout_genre1.setVisibility(View.VISIBLE);

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

    private void Like() {
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LIKE){
                    like.setImageResource(R.drawable.if_heart_119_111093);
                    LIKE = false;
                }else {
                    like.setImageResource(R.drawable.if_heart_1814104);
                    LIKE = true;
                }
            }
        });

    }

    private void recyclerViewTouch() {

        recyclerView_trailers.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        System.out.println("The RecyclerView is not scrolling");
                        helper.enableSwipeBack();
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        System.out.println("Scrolling now");
                        helper.disableSwipeBack();
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        System.out.println("Scroll Settling");
                        helper.disableSwipeBack();
                        break;

                }
            }
        });

    }


    @Override
    public void onBackPressed() {
        helper.finish();
    }

}