package com.vacuum.app.cinema.Utility;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestMovie {
    String ROOT_URL = "https://mohamedebrahim.000webhostapp.com/";

    public RequestMovie(final String id, final String title, final Context mContext)
    {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);
        api.requestMovie(
                id,
                title
        ).enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.isSuccessful()) {
                    String responsse ;
                    try {
                        responsse  = response.body().string();
                        Log.e("TAG", "RequestMovie "+responsse);

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
