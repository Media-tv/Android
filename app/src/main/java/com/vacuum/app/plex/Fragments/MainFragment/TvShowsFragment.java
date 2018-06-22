package com.vacuum.app.plex.Fragments.MainFragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.vacuum.app.plex.Adapter.MoviesAdapter;
import com.vacuum.app.plex.Fragments.MoreFragment;
import com.vacuum.app.plex.MainActivity;
import com.vacuum.app.plex.Model.Movie;
import com.vacuum.app.plex.Model.MoviesResponse;
import com.vacuum.app.plex.R;
import com.vacuum.app.plex.Utility.ApiClient;
import com.vacuum.app.plex.Utility.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vacuum.app.plex.Fragments.MainFragment.HomeFragment.apiService;

/**
 * Created by Home on 2/19/2018.
 */

public class TvShowsFragment extends Fragment implements View.OnClickListener{
    RecyclerView movies_recycler4_tv_popular,movies_recycler5_tv_toprated;
    ProgressBar progressBar;
    Context mContext;
    LinearLayout layout;
    String TMBDB_API_KEY;
    TextView more_Popular_tv,more_top_rated_tv;
    FirebaseAnalytics mFirebaseAnalytics;
    public static final String TAG_TVSHOWS_FRAGMENT = "TAG_TVSHOWS_FRAGMENT";
    AdView adView_tvshow_fragment,adView_tvshow_fragment2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tvshowsfragment, container, false);
        mContext = this.getContext();

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(mContext);
        mFirebaseAnalytics.setCurrentScreen(getActivity(), "TvShowsFragment", null );

        movies_recycler4_tv_popular=  view.findViewById(R.id.movies_recycler4_tv_popular);
        movies_recycler5_tv_toprated=  view.findViewById(R.id.movies_recycler5_tv_toprated);

        more_Popular_tv=  view.findViewById(R.id.more_Popular_tv);
        more_top_rated_tv=  view.findViewById(R.id.more_top_rated_tv);

        adView_tvshow_fragment=  view.findViewById(R.id.adView_tvshow_fragment);
        adView_tvshow_fragment2=  view.findViewById(R.id.adView_tvshow_fragment2);
        more_Popular_tv.setOnClickListener(this);
        more_top_rated_tv.setOnClickListener(this);

        progressBar =  view.findViewById(R.id.progressBar_tv);
        layout =  view.findViewById(R.id.layout_tv);

        movies_recycler4_tv_popular.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        movies_recycler5_tv_toprated.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

        SharedPreferences prefs = mContext.getSharedPreferences("Plex", Activity.MODE_PRIVATE);
        TMBDB_API_KEY = prefs.getString("TMBDB_API_KEY",null);
        retrofit();
        Ads();
        return view;
    }
    private void Ads() {
        AdRequest adRequest = new AdRequest.Builder().build();
        adView_tvshow_fragment.loadAd(adRequest);
        adView_tvshow_fragment2.loadAd(adRequest);
    }


    private void retrofit() {
        //String API_KEY = getResources().getString(R.string.TMBDB_API_KEY);
        //progressBar.setVisibility(View.VISIBLE);
        //layout.setVisibility(View.GONE);
        apiService =
                ApiClient.getClient(mContext).create(ApiInterface.class);

        //====================================================================================
        //====================================================================================
        //====================================================================================
        Call<MoviesResponse> call_popularTV = apiService.getpopularTV(TMBDB_API_KEY,1);
        call_popularTV.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse>call, Response<MoviesResponse> response) {
                List<Movie> movies = response.body().getResults();
                movies_recycler4_tv_popular.setAdapter(new MoviesAdapter(movies, mContext));
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
        Call<MoviesResponse> call_getTopRatedTV = apiService.getTopRatedTV(TMBDB_API_KEY,1);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.more_Popular_tv:
                switchFragment("more_Popular_tv");
                break;
            case R.id.more_top_rated_tv:
                switchFragment("more_top_rated_tv");
                break;
        }
    }

    private void switchFragment(String value) {
        Fragment fragment = new MoreFragment();
        Bundle bundle = new Bundle();
        bundle.putString("value", value);
        fragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        /*fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);*/
        fragmentTransaction.replace(R.id.frame, fragment, MoreFragment.TAG_MORE_FRAGMENT);
        fragmentTransaction.addToBackStack(MainActivity.CURRENT_TAG);
        fragmentTransaction.commit();
    }


}