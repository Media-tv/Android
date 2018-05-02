package com.vacuum.app.cinema.Utility;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestMovie {
    public RequestMovie(String id, String title, final Context mContext)
    {
        String ROOT_URL = "https://mohamedebrahim.000webhostapp.com/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
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
                        System.out.println("====================================================");
                        System.out.println(responsse);
                        Toast.makeText(mContext,responsse, Toast.LENGTH_SHORT).show();

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
