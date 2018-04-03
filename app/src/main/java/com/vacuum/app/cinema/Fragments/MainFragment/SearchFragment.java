package com.vacuum.app.cinema.Fragments.MainFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vacuum.app.cinema.Adapter.MoviesAdapter;
import com.vacuum.app.cinema.Adapter.SearchAdapter;
import com.vacuum.app.cinema.Adapter.TrailerAdapter;
import com.vacuum.app.cinema.Model.Movie;
import com.vacuum.app.cinema.Model.MovieDetails;
import com.vacuum.app.cinema.Model.MoviesResponse;
import com.vacuum.app.cinema.Model.Trailer;
import com.vacuum.app.cinema.Model.TrailerResponse;
import com.vacuum.app.cinema.R;
import com.vacuum.app.cinema.Utility.ApiClient;
import com.vacuum.app.cinema.Utility.ApiInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Home on 2/19/2018.
 */

public class SearchFragment extends Fragment {
    Context mContext;
    RecyclerView recyclerView_search;
    LinearLayout layout_search;
    EditText edit_query;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        recyclerView_search = view.findViewById(R.id.recyclerView_search);
        layout_search = view.findViewById(R.id.layout_search);

        mContext = this.getActivity();
        edit_query = view.findViewById(R.id.edit_query);
        edit_query.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                recyclerView_search.setVisibility(View.VISIBLE);
                layout_search.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getDate(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String ed_text = edit_query.getText().toString().trim();
                if(ed_text.isEmpty() || ed_text.length() == 0 || ed_text.equals("") || ed_text == null)
                {
                    //EditText is empty
                    recyclerView_search.setVisibility(View.GONE);
                    layout_search.setVisibility(View.VISIBLE);
                }
                else
                {
                    //EditText is not empty
                }
            }
        });

        return view;
    }

    private void getDate(CharSequence Char) {
        String API_KEY = getString(R.string.TMBDB_API_KEY);
        String query = Char.toString();

        ApiInterface apiService =
                ApiClient.getClient(mContext).create(ApiInterface.class);


        //setRecyclerView_search
        //==================================================================
        Call<MoviesResponse> call_RecyclerView_search = apiService.getMovieSearch(query,API_KEY);
        call_RecyclerView_search.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                List<Movie> movies = null;
                List<Movie> movies2 = new ArrayList<>();

                try {
                    if (response.body().getResults() != null )
                    {   movies = response.body().getResults();
                        for(Movie s:movies){
                            if (!s.getMedia_type().equals("person")){
                                movies2.add(s);
                            }
                        }

                        recyclerView_search.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                        recyclerView_search.setAdapter(new SearchAdapter(movies2, mContext));
                    }
                }catch (Exception e ){
                    Log.e("Exception::",e.toString());
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("tag", t.toString());
            }
        });
    }
}