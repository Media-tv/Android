package com.vacuum.app.cinema.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vacuum.app.cinema.Adapter.CreditsAdapter;
import com.vacuum.app.cinema.Adapter.CrewAdapter;
import com.vacuum.app.cinema.Adapter.EpisodesAdapter;
import com.vacuum.app.cinema.Adapter.ImagesAdapter;
import com.vacuum.app.cinema.Adapter.TrailerAdapter;
import com.vacuum.app.cinema.Model.Backdrop;
import com.vacuum.app.cinema.Model.Cast;
import com.vacuum.app.cinema.Model.Credits;
import com.vacuum.app.cinema.Model.Crew;
import com.vacuum.app.cinema.Model.Episode;
import com.vacuum.app.cinema.Model.Genre;
import com.vacuum.app.cinema.Model.Images_tmdb;
import com.vacuum.app.cinema.Model.Movie;
import com.vacuum.app.cinema.Model.SeasonDetails;
import com.vacuum.app.cinema.Model.TVDetails;
import com.vacuum.app.cinema.Model.Trailer;
import com.vacuum.app.cinema.Model.TrailerResponse;
import com.vacuum.app.cinema.R;
import com.vacuum.app.cinema.Utility.ApiClient;
import com.vacuum.app.cinema.Utility.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by Home on 3/3/2018.
 */

public class DetailsTV_Fragment extends Fragment {

    private static boolean LIKE = true;
    public static final String TAG_DetailsTV_Fragment = "TAG_DetailsTV_Fragment";

    TextView title,year,rating,overview,runtime,voteCount,genre1,genre2,genre3,number_of_seasons,number_of_episodes,id_number;
    ImageView cover;
    Context mContext;
    RecyclerView recyclerView_trailers,recyclerView_actors,recyclerView_crew,recyclerView_images,recyclerView_episodes;
    RelativeLayout layout_genre1,layout_genre2,layout_genre3;
    LinearLayout trailer_layout,actors_layout,crew_layout,image_layout,episodes_layout;
    RatingBar ratingBar;
    ImageView like,watch;
    ProgressBar progressBar;
    LinearLayout layout;
    Spinner spinner;
    int x;
    Movie movie;
    Handler mHandler;
    Runnable myRunnable;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.details_tv_fragment_layout, container, false);

        mContext = this.getActivity();

        title = view.findViewById(R.id.title);
        year = view.findViewById(R.id.year);
        rating = view.findViewById(R.id.rating);
        overview = view.findViewById(R.id.overview);
        runtime = view.findViewById(R.id.runtime);
        cover = view.findViewById(R.id.cover);
        ratingBar = view.findViewById(R.id.ratingBar);
        voteCount = view.findViewById(R.id.voteCount);
        id_number = view.findViewById(R.id.id_number);

        number_of_episodes = view.findViewById(R.id.number_of_episodes);
        number_of_seasons = view.findViewById(R.id.number_of_seasons);

        genre1 = view.findViewById(R.id.genre1);
        genre2 = view.findViewById(R.id.genre2);
        genre3 = view.findViewById(R.id.genre3);
        layout_genre1 = view.findViewById(R.id.layout_genre1);
        layout_genre2 = view.findViewById(R.id.layout_genre2);
        layout_genre3 = view.findViewById(R.id.layout_genre3);
        like = view.findViewById(R.id.like);
        watch = view.findViewById(R.id.watch);
        trailer_layout = view.findViewById(R.id.trailer_layout);
        actors_layout = view.findViewById(R.id.actors_layout);
        image_layout = view.findViewById(R.id.image_layout);
        episodes_layout = view.findViewById(R.id.episodes_layout);

        recyclerView_trailers = view.findViewById(R.id.recyclerView_trailers);
        recyclerView_actors = view.findViewById(R.id.recyclerView_actors);
        recyclerView_crew = view.findViewById(R.id.recyclerView_crew);
        recyclerView_images = view.findViewById(R.id.recyclerView_images);
        recyclerView_episodes = view.findViewById(R.id.recyclerView_episodes);

        crew_layout = view.findViewById(R.id.crew_layout);

        spinner= view.findViewById(R.id.spinner_episodes);


        progressBar =  view.findViewById(R.id.progressBar_tv);
        layout =  view.findViewById(R.id.layout_tv);




        movie = (Movie)getArguments().getSerializable("movie");
        x= movie.getId();





        retrofit();
        Like();
        return view;
    }

    private void retrofit() {

        progressBar.setVisibility(View.VISIBLE);
        layout.setVisibility(View.GONE);

        final String API_KEY = getResources().getString(R.string.TMBDB_API_KEY);
        final ApiInterface apiService =
                ApiClient.getClient(mContext).create(ApiInterface.class);

        Call<TVDetails> call_TV_details = apiService.getTVDetails(x,API_KEY);
        call_TV_details.enqueue(new Callback<TVDetails>() {
            @Override
            public void onResponse(Call<TVDetails> call, Response<TVDetails> response) {
                TVDetails m = response.body();


                    try{
                        title.setText(m.getOriginalName());
                        year.setText(m.getLastAirDate().substring(0,4));
                        rating.setText(m.getVoteAverage().toString());
                        overview.setText(m.getOverview());
                        ratingBar.setRating(m.getVoteAverage().floatValue()/2);
                        voteCount.setText(m.getVoteCount().toString());
                        id_number.setText("ID:" + String.valueOf(x));

                        number_of_episodes.setText("Number of episodes: "+m.getNumberOfEpisodes().toString());
                        number_of_seasons.setText("Number of seasons: "+m.getNumberOfSeasons().toString());
                        setgenre(m.getGenres(),m.getGenres().size());
                        String cover_string_link ;
                        if(movie.getBackdropPath() == null){
                            cover_string_link = "http://image.tmdb.org/t/p/w780"+m.getPosterPath().toString();
                        }else {
                            cover_string_link = "http://image.tmdb.org/t/p/w780"+m.getBackdropPath().toString();
                        }
                        Glide.with(mContext)
                                .load(cover_string_link)
                                .transition(withCrossFade())
                                .into(cover);
                        List<String> seasons = new ArrayList<>();
                        for(int i=0;i< m.getNumberOfSeasons();i++){
                            seasons.add(i,"SEASON "+(i+1));
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, R.layout.spinner_item_layout, seasons);
                        spinner.setAdapter(adapter);
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                // your code here
                                episodes(position+1);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parentView) {
                            }

                        });

                        progressBar.setVisibility(View.GONE);
                        layout.setVisibility(View.VISIBLE);

                    }catch (Exception e){
                        Log.i("TAG :",e.toString());
                    }

            }

            @Override
            public void onFailure(Call<TVDetails> call, Throwable t) {
                // Log error here since request failed
                Log.e("TAG :", t.toString());
            }
        });


        //setRecyclerView_actors
        //==================================================================
        Call<Credits> call_recyclerView_actors = apiService.getTVCredits(x,API_KEY);
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

        //call_recyclerView_crew
        //==================================================================
        Call<Credits> call_recyclerView_crew = apiService.getTVCredits(x,API_KEY);
        call_recyclerView_crew.enqueue(new Callback<Credits>() {
            @Override
            public void onResponse(Call<Credits> call, Response<Credits> response) {
                List<Crew> crews = null;
                try {
                    crews = response.body().getCrew();
                    crew_layout.setVisibility(View.VISIBLE);
                    recyclerView_crew.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                    recyclerView_crew.setAdapter(new CrewAdapter(crews, mContext));
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

        //setRecyclerView_images
        //==================================================================
        Call<Images_tmdb> call_RecyclerView_images = apiService.getImages("tv",x,API_KEY);
        call_RecyclerView_images.enqueue(new Callback<Images_tmdb>() {
            @Override
            public void onResponse(Call<Images_tmdb> call, Response<Images_tmdb> response) {
                List<Backdrop> posters;
                //List<Poster> posters;

                    posters = response.body().getBackdrops();
                    image_layout.setVisibility(View.VISIBLE);
                    recyclerView_images.setLayoutManager(new LinearLayoutManager(mContext,
                            LinearLayoutManager.HORIZONTAL, false));
                    recyclerView_images.setAdapter(new ImagesAdapter(posters, mContext));
                    change_backdrop(posters);

            }

            @Override
            public void onFailure(Call<Images_tmdb> call, Throwable t) {
                // Log error here since request failed
                Log.e("tag", t.toString());
            }
        });
        //setRecyclerView_trailers
        //==================================================================
        Call<TrailerResponse> call_RecyclerView_trailers = apiService.gettrailers("tv",x,API_KEY);
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

    private void episodes(int number) {
        final String API_KEY = getResources().getString(R.string.TMBDB_API_KEY);
        final ApiInterface apiService =
                ApiClient.getClient(mContext).create(ApiInterface.class);
        Call<SeasonDetails> call_RecyclerView_episodes = apiService.getepisodes(x,number,API_KEY);
                                   call_RecyclerView_episodes.enqueue(new Callback<SeasonDetails>() {
                                       @Override
                                       public void onResponse(Call<SeasonDetails> call, Response<SeasonDetails> response) {
                                           List<Episode> episodes;

                                           episodes = response.body().getEpisodes();
                                           episodes_layout.setVisibility(View.VISIBLE);
                                           recyclerView_episodes.setLayoutManager(new LinearLayoutManager(mContext,
                                                   LinearLayoutManager.VERTICAL, false));
                                           recyclerView_episodes.setAdapter(new EpisodesAdapter(episodes, mContext));
                                           recyclerView_episodes.setNestedScrollingEnabled(false);


                                       }

                                       @Override
                                       public void onFailure(Call<SeasonDetails> call, Throwable t) {
                                           Log.e("tag", t.toString());
                                       }
                                   });
    }

    private void change_backdrop(final List<Backdrop> imageArray) {
        mHandler=  new Handler();
        myRunnable = new Runnable() {
            int i = 0;

            public void run() {
                Glide.with(mContext)
                        .load("http://image.tmdb.org/t/p/w500"+imageArray.get(i).getFilePath())
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
    @Override
    public void onPause() {
        super.onPause();
        Log.e("TAG","Stop handler ");
        if(myRunnable != null){
            mHandler.removeCallbacksAndMessages(null);
        }


    }

}
