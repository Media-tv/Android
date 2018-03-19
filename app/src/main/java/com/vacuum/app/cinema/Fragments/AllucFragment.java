package com.vacuum.app.cinema.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vacuum.app.cinema.Adapter.AllucAdapter;
import com.vacuum.app.cinema.Model.Alluc;
import com.vacuum.app.cinema.Model.Movie;
import com.vacuum.app.cinema.Model.allucResults;
import com.vacuum.app.cinema.R;
import com.vacuum.app.cinema.Utility.ApiClient;
import com.vacuum.app.cinema.Utility.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Home on 3/3/2018.
 */

public class AllucFragment extends Fragment {



    Context mContext;
    RecyclerView recyclerView_watch;
    Movie movie;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.alluc_fragment_layout, container, false);
        recyclerView_watch = view.findViewById(R.id.recyclerView_watch);
        mContext = this.getActivity();

        movie = (Movie) getActivity().getIntent().getSerializableExtra("movie");


        //fragment();
        getDate();
        onbackpressed(view);
        return view;
    }




    private void getDate() {
        String Alluc_API_KEY = getString(R.string.Alluc_API_KEY);
        ApiInterface apiService2 =
                ApiClient.getClient().create(ApiInterface.class);
        String url = "https://www.alluc.ee/api/search/download/?apikey="+Alluc_API_KEY+"&query="+movie.getTitle()+"+"+movie.getReleaseDate().substring(0, 4)+"&count=5&from=0&getmeta=0";

        //setRecyclerView_trailers
        //==================================================================
        Call<allucResults> call_RecyclerView_Alluc = apiService2.profilePicture(url);
        call_RecyclerView_Alluc.enqueue(new Callback<allucResults>() {
            @Override
            public void onResponse(Call<allucResults> call, Response<allucResults> response) {
                List<Alluc> trailers = null;
                try {
                    trailers = response.body().getAlluc();
                    if (trailers != null)
                    {
                        recyclerView_watch.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                        recyclerView_watch.setAdapter(new AllucAdapter(trailers, mContext));
                    }
                }catch (Exception e ){
                    Log.e("Exception::",e.toString());
                }
            }

            @Override
            public void onFailure(Call<allucResults> call, Throwable t) {
                // Log error here since request failed
                Log.e("tag", t.toString());
            }
        });

    }
    private void fragment() {
        WatchFragment watchFragment = new WatchFragment();
        FragmentTransaction manager = ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction();
        manager.replace(R.id.frame_detailsFragment, watchFragment);
        manager.addToBackStack(null).commit();
    }
    private void onbackpressed(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    FragmentManager fm = getFragmentManager();
                    fm.popBackStack();
                    return true;
                }
                return false;
            }
        } );
    }




}
