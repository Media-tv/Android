package com.vacuum.app.cinema.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.vacuum.app.cinema.Adapter.MoviesAdapter;
import com.vacuum.app.cinema.MainActivity;
import com.vacuum.app.cinema.Model.Movie;
import com.vacuum.app.cinema.Model.MoviesResponse;
import com.vacuum.app.cinema.R;
import com.vacuum.app.cinema.Utility.ApiClient;
import com.vacuum.app.cinema.Utility.ApiInterface;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Home on 2/19/2018.
 */

public class HomeFragment extends Fragment {

    private final static String API_KEY = "";
    RecyclerView movies_recycler1_UpComing,movies_recycler2_popular,movies_recycler3_top_rated,movies_recycler4_tv_popular,movies_recycler5_tv_toprated;
    Context mContext;
    SliderLayout mDemoSlider;
    ProgressBar progressBar;
    LinearLayout layout;
    //LinearLayoutManager layoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one, container, false);

        mContext = this.getActivity();
        movies_recycler1_UpComing= (RecyclerView) view.findViewById(R.id.movies_recycler1_UpComing);
        movies_recycler2_popular= (RecyclerView) view.findViewById(R.id.movies_recycler2_popular);
        movies_recycler3_top_rated= (RecyclerView) view.findViewById(R.id.movies_recycler3_top_rated);
        movies_recycler4_tv_popular= (RecyclerView) view.findViewById(R.id.movies_recycler4_tv_popular);
        movies_recycler5_tv_toprated= (RecyclerView) view.findViewById(R.id.movies_recycler5_tv_toprated);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        layout = (LinearLayout) view.findViewById(R.id.layout);

        mDemoSlider = (SliderLayout)view.findViewById(R.id.slider);


        //layoutManager= new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        movies_recycler1_UpComing.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        movies_recycler2_popular.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        movies_recycler3_top_rated.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        movies_recycler4_tv_popular.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        movies_recycler5_tv_toprated.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));




        retrofit();
        setupslider();
        return view;
    }

    private void setupslider() {
        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("Diriliş Ertuğrul", "https://scontent-cai1-1.xx.fbcdn.net/v/t31.0-8/22905018_1209112999232380_3565256246963278246_o.jpg?oh=01056944fbe2dbff150f61f7740f7f2d&oe=5B132A7B");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("Game of Thrones", "http://www.shmee.me/wp-content/uploads/2016/06/Game-of-Thrones-Season-6-HEADER.jpg");



        for(String name : url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(mContext);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);


    }


    private void retrofit(){




        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<MoviesResponse> call_UpComing = apiService.getupcomingMovies(API_KEY);
        call_UpComing.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse>call, Response<MoviesResponse> response) {
                List<Movie> movies = response.body().getResults();
                movies_recycler1_UpComing.setAdapter(new MoviesAdapter(movies, mContext));
                progressBar.setVisibility(View.GONE);
                layout.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailure(Call<MoviesResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e("tag", t.toString());
            }
        });
        //====================================================================================
        //====================================================================================
        //====================================================================================
        Call<MoviesResponse> call_popular = apiService.getpopularMovies(API_KEY);
        call_popular.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse>call, Response<MoviesResponse> response) {
                List<Movie> movies = response.body().getResults();
                movies_recycler2_popular.setAdapter(new MoviesAdapter(movies, mContext));
            }

            @Override
            public void onFailure(Call<MoviesResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e("tag", t.toString());
            }
        });
        //====================================================================================
        //====================================================================================
        //====================================================================================
        Call<MoviesResponse> call_top_rated = apiService.getTopRatedMovies(API_KEY);
        call_top_rated.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse>call, Response<MoviesResponse> response) {
                List<Movie> movies = response.body().getResults();
                movies_recycler3_top_rated.setAdapter(new MoviesAdapter(movies, mContext));
            }

            @Override
            public void onFailure(Call<MoviesResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e("tag", t.toString());
            }
        });


        //====================================================================================
        //====================================================================================
        //====================================================================================
        Call<MoviesResponse> call_popularTV = apiService.getpopularTV(API_KEY);
        call_popularTV.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse>call, Response<MoviesResponse> response) {
                List<Movie> movies = response.body().getResults();
                movies_recycler4_tv_popular.setAdapter(new MoviesAdapter(movies, mContext));
            }

            @Override
            public void onFailure(Call<MoviesResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e("tag", t.toString());
            }
        });



        //====================================================================================
        //====================================================================================
        //====================================================================================
        Call<MoviesResponse> call_getTopRatedTV = apiService.getTopRatedTV(API_KEY);
        call_getTopRatedTV.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse>call, Response<MoviesResponse> response) {
                List<Movie> movies = response.body().getResults();
                movies_recycler5_tv_toprated.setAdapter(new MoviesAdapter(movies, mContext));
            }

            @Override
            public void onFailure(Call<MoviesResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e("tag", t.toString());
            }
        });
    }
}
