package com.vacuum.app.plex.Utility;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AddMovie {
    String ROOT_URL = "https://mohamedebrahim.000webhostapp.com/";

    public AddMovie(final Context mContext,
                    String id,
                    final String title,
                    final String file_id,
//======================================================
                    int id_tvseries_tmdb,
                    String name_tv_series,
                    int season_number,
                    String episode_name,
                    int Episode_number,
                    int episode_id_tmdb)
    {






        ApiInterface api = ApiClient.getClient(mContext,ROOT_URL).create(ApiInterface.class);
        api.addMovie(
                id,
                title,
                file_id,
//===============================================
                id_tvseries_tmdb,
                name_tv_series,
                season_number,
                episode_name,
                Episode_number,
                episode_id_tmdb
        ).enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    String responsse ;
                    try {
                        responsse  = response.body().string();
                        Log.e("TAG", "Add Movie "+responsse);
                        new GetOpenload(mContext,file_id,title);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TAG", "Unable to submit post to API.");
            }
        });


    }
}
