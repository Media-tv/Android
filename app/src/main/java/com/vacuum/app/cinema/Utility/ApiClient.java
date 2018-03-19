package com.vacuum.app.cinema.Utility;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Home on 2/20/2018.
 */

public class ApiClient {

    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;
    //private static Retrofit retrofit2 = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
