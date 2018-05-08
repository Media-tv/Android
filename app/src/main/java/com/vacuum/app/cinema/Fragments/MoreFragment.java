package com.vacuum.app.cinema.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.vacuum.app.cinema.Adapter.MoviesAdapter;
import com.vacuum.app.cinema.Model.Movie;
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
 * Created by Home on 3/11/2018.
 */

public class MoreFragment extends Fragment {
    Context mContext;
    public static final String TAG_MORE_FRAGMENT = "TAG_MORE_FRAGMENT";
    RecyclerView fragment_grid_recylerview;
    String value;
    private boolean userScrolled = true;
    private static int  pageIndex ;

    private LinearLayoutManager mLayoutManager;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private  RelativeLayout bottomLayout;
    MoviesAdapter moviesAdapter;
    List<Movie> movies = new ArrayList<>();
    List<Movie> movies2 = new ArrayList<>();
    String TMBDB_API_KEY;
    Handler mHandler;
    Runnable myRunnable;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        mContext = this.getContext();
        fragment_grid_recylerview=  view.findViewById(R.id.fragment_grid_recylerview);
        bottomLayout = view.findViewById(R.id.loadItemsLayout_recyclerView);
        mLayoutManager = new GridLayoutManager(mContext,3);
        fragment_grid_recylerview.setLayoutManager(mLayoutManager);
        pageIndex = 1;

        SharedPreferences prefs = mContext.getSharedPreferences("Plex", Activity.MODE_PRIVATE);
        TMBDB_API_KEY = prefs.getString("TMBDB_API_KEY",null);
        Bundle bundle = this.getArguments();
        value = bundle.getString("value");

        if(myRunnable != null){
            mHandler.removeCallbacks(myRunnable);

        }

        retrofit();
        Pagination();
        return view;
    }

    private void Pagination() {
        fragment_grid_recylerview.addOnScrollListener(new RecyclerView.OnScrollListener() {

                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView,
                                                     int newState) {

                        super.onScrollStateChanged(recyclerView, newState);

                        // If scroll state is touch scroll then set userScrolled
                        // true
                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                            userScrolled = true;

                        }

                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx,
                                           int dy) {

                        super.onScrolled(recyclerView, dx, dy);
                        // Here get the child count, item count and visibleitems
                        // from layout manager

                        visibleItemCount = mLayoutManager.getChildCount();
                        totalItemCount = mLayoutManager.getItemCount();
                        pastVisiblesItems = mLayoutManager
                                .findFirstVisibleItemPosition();


                        // Now check if userScrolled is true and also check if
                        // the item is end then update recycler view and set
                        // userScrolled to false
                        if (userScrolled
                                && (visibleItemCount + pastVisiblesItems) == totalItemCount) {
                            userScrolled = false;
                            updateRecyclerView();
                        }

                    }

                });
    }

    private void updateRecyclerView() {

            bottomLayout.setVisibility(View.VISIBLE);

             /*mHandler = new Handler();
             mHandler.postDelayed(new Runnable() {
                 @Override
                 public void run() {
                     retrofit();
                     Toast.makeText(mContext, "Items Updated.",
                             Toast.LENGTH_SHORT).show();
                     bottomLayout.setVisibility(View.GONE);
                 }
             },2000);*/
            /*new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    retrofit();
                    Toast.makeText(getActivity(), "Items Updated.",
                            Toast.LENGTH_SHORT).show();
                    bottomLayout.setVisibility(View.GONE);

                }
            }, 3000);*/

        mHandler=  new Handler();
        myRunnable = new Runnable() {
            public void run() {
                retrofit();
                Toast.makeText(mContext, "Items Updated.",
                        Toast.LENGTH_SHORT).show();
                bottomLayout.setVisibility(View.GONE);
            }
        };
        mHandler.postDelayed(myRunnable,2000);


    }


    private void retrofit() {



        //====================================================================================
        //====================================================================================
        //====================================================================================
        Call<MoviesResponse> call = getapiService();
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                pageIndex++;

                if(pageIndex >=2){
                    movies2 = response.body().getResults();
                    movies.addAll(movies2);
                    moviesAdapter = new MoviesAdapter(movies, mContext);
                    fragment_grid_recylerview.setAdapter(moviesAdapter);
                    fragment_grid_recylerview.scrollToPosition(pastVisiblesItems);

                }else {
                    movies = response.body().getResults();
                    moviesAdapter = new MoviesAdapter(movies, mContext);
                    fragment_grid_recylerview.setAdapter(moviesAdapter);
                }

            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.e("tag", t.toString());
            }
        });

    }
    private Call getapiService() {

        ApiInterface apiService =
                ApiClient.getClient(mContext).create(ApiInterface.class);
        Call<MoviesResponse> calll = null;
        switch (value){
            case "more_Popular_tv":
                calll = apiService.getpopularTV(TMBDB_API_KEY,pageIndex);
                break;
            case "more_top_rated_tv":
                calll = apiService.getTopRatedTV(TMBDB_API_KEY,pageIndex);
                break;
            case "more_upcoming":
                calll = apiService.getupcomingMovies(TMBDB_API_KEY,pageIndex);
                break;
            case "more_top_rated":
                calll = apiService.getTopRatedMovies(TMBDB_API_KEY,pageIndex);
                break;
            case "more_Popular":
                calll = apiService.getpopularMovies(TMBDB_API_KEY,pageIndex);
                break;
        }
        return calll;
    }

    @Override
    public void onPause() {
        super.onPause();
        if(myRunnable != null){
            mHandler.removeCallbacksAndMessages(myRunnable);
        }
        Log.i("TAG : Fragment", "onPause");
    }
}

