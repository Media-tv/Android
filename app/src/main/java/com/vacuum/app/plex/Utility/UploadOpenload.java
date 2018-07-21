package com.vacuum.app.plex.Utility;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.vacuum.app.plex.Model.Link;
import com.vacuum.app.plex.Model.OpenloadResult;
import com.vacuum.app.plex.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadOpenload {
    String OPENLOAD_API_Login,OPENLOAD_API_KEY;
    ApiInterface api;
    Context mContext;
    String url,title,id_,year;
    Link l;
    public UploadOpenload(final Context mContext, Link l){

        this.mContext = mContext;
        this.l = l;

        this.url = l.getUrl();
        this.title = l.getTitle();
        this.year = l.getYear();
        this.id_ = l.getId();


        String ROOT_URL = "https://mohamedebrahim.000webhostapp.com/";

        SharedPreferences prefs = mContext.getSharedPreferences("Plex", Activity.MODE_PRIVATE);
        OPENLOAD_API_Login = prefs.getString("OPENLOAD_API_Login",null);
        OPENLOAD_API_KEY = prefs.getString("OPENLOAD_API_KEY",null);

        api = ApiClient.getClient(mContext,ROOT_URL).create(ApiInterface.class);
        UploadOpenload2(url);
    }

    private void UploadOpenload2(String raw) {
        Call<OpenloadResult> call_details =
                api.uploadOpenload("https://api.openload.co/1/remotedl/add?login="+OPENLOAD_API_Login+"&key="+OPENLOAD_API_KEY+"&url="+raw+"&folder=5457914");
        call_details.enqueue(new Callback<OpenloadResult>() {
            @Override
            public void onResponse(Call<OpenloadResult> call, Response<OpenloadResult> response) {

                if(response.body().getOpenload().getId() != null){
                    String x = response.body().getOpenload().getId();
                    getfileid(x);
                }

            }
            @Override
            public void onFailure(Call<OpenloadResult> call, Throwable t) {
                Log.e("TAG", t.toString());
            }
        });
    }

    private void getfileid(final String id) {
        Call<ResponseBody> call_details =
                api.uploadOpenload_id("https://api.openload.co/1/remotedl/status?login="+OPENLOAD_API_Login+"&key="+OPENLOAD_API_KEY+"&id="+id);
        call_details.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                String remoteResponse= null;
                try {
                    remoteResponse = response.body().string();
                    Log.e("TAG", remoteResponse);
                    JSONObject response2 = new JSONObject(remoteResponse);
                    JSONObject result = response2.getJSONObject("result");
                    JSONObject arr = result.getJSONObject(id);
                    String file_id = arr.getString("extid");
                    if(file_id == "false"){
                        Toast.makeText(mContext, "Request!", Toast.LENGTH_SHORT).show();
                    }else {
                        new AddMovie(mContext,id_,
                                title+" : "+year,
                                file_id,
                                //========================================
                                l.getId_tvseries_tmdb(),
                                l.getName_tv_series(),
                                l.getSeason_number(),
                                l.getEpisode_name(),
                                l.getEpisode_number(),
                                l.getEpisode_id_tmdb());
                    }
                } catch (JSONException e) {
                    Log.e("TAG:forecast", e.toString());
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TAG", t.toString());
            }
        });
    }

}
