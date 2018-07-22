package com.vacuum.app.plex.Utility;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.vacuum.app.plex.Model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.vacuum.app.plex.Splash.SplashScreen.MY_PREFS_NAME;

public class Balance {
    private ApiInterface apiService;
    private Context mContext;
    private Boolean enough_points = true;
    private int points,year;
    private String user_id;

    public Balance(Context mContext, int year){
        this.mContext = mContext;
        this.year = year;
        Log.e("TAG :points1",String.valueOf(points));
        SharedPreferences prefs = mContext.getSharedPreferences("Plex", Activity.MODE_PRIVATE);
        user_id = prefs.getString("id",null);
        points = prefs.getInt("points",0);
        Log.e("TAG :points2",String.valueOf(points));


    }
    public Boolean calculate_points() {
        if (year == 2018 &&points>=10) { points = points-10; enough_points=true;}
        else if (year < 2018 &&year>=2012 &&points>=7) { points = points-7;enough_points=true; }
        else if (year < 2012 &&year>=2000&&points>=5) { points = points-5;enough_points=true; }
        else if (year < 2000&&points>=2) { points = points-2;enough_points=true; }
        else {enough_points=false; }
        points();
        return enough_points;
    }
    private void points() {
        String BASE_URL = "https://mohamedebrahim.000webhostapp.com/";

        apiService =ApiClient.getClient(mContext,BASE_URL).create(ApiInterface.class);
        Log.e("TAG :points",String.valueOf(points));
        Call<User> call_details = apiService.points(user_id,String.valueOf(points));
        call_details.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User m = response.body();
                SharedPreferences.Editor editor = mContext.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putInt("points",m.getPoints());
                editor.apply();
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("TAG", t.toString());
            }
        });
    }
}
