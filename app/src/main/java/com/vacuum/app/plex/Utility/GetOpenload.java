package com.vacuum.app.plex.Utility;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.vacuum.app.plex.Activities.WatchActivity;
import com.vacuum.app.plex.Model.OpenloadResult;
import com.vacuum.app.plex.Model.OpenloadThumbnail;
import com.vacuum.app.plex.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetOpenload {

    Context mContext;
    String OPENLOAD_API_Login,OPENLOAD_API_KEY,file_id,ticket,openload_thumbnail_url,title;
    Boolean found_captcha = false;
    Dialog dialog;
    String BASE_URL = "https://mohamedebrahim.000webhostapp.com/";
    public GetOpenload(Context mContext,String file_id,String title){
        this.file_id = file_id;
        this.title = title;
        this.mContext = mContext;
        retrofit_1();
    }


    private void retrofit_1() {

        SharedPreferences prefs = mContext.getSharedPreferences("Plex", Activity.MODE_PRIVATE);
        OPENLOAD_API_Login = prefs.getString("OPENLOAD_API_Login",null);
        OPENLOAD_API_KEY = prefs.getString("OPENLOAD_API_KEY",null);

        //OPENLOAD_API_Login = mContext.getResources().getString(R.string.OPENLOAD_API_Login);
       // OPENLOAD_API_KEY = mContext.getResources().getString(R.string.OPENLOAD_API_KEY);
        String full_url = "https://api.openload.co/1/file/dlticket?file=" + file_id + "&login=" + OPENLOAD_API_Login + "&key=" + OPENLOAD_API_KEY;

        Log.e("TAG","retrofit_1");
        ApiInterface apiService =
                ApiClient.getClient(mContext,BASE_URL).create(ApiInterface.class);
        Call<OpenloadResult> call_slider = apiService.getOpenload(full_url);
        call_slider.enqueue(new Callback<OpenloadResult>() {
            @Override
            public void onResponse(Call<OpenloadResult> call, Response<OpenloadResult> response) {
                OpenloadResult open = response.body();
                ticket = open.getOpenload().getTicket();
                if(found_captcha){
                    AlertDialog(open.getOpenload().getCaptchaUrl());
                }else {
                    String url = "https://api.openload.co/1/file/dl?file=" + file_id + "&ticket=" + ticket;
                    retrofit_2(url);
                }
            }
            @Override
            public void onFailure(Call<OpenloadResult> call, Throwable t) {
                // Log error here since request failed
                Log.e("TAG: error ", t.toString());
            }
        });

        //=================================================
        String thumbnail_url = "https://api.openload.co/1/file/getsplash?login=" + OPENLOAD_API_Login + "&key=" + OPENLOAD_API_KEY + "&file=" + file_id;
        Call<OpenloadThumbnail> call_openload_thumbnail = apiService.getOpenloadThumbnail(thumbnail_url);
        call_openload_thumbnail.enqueue(new Callback<OpenloadThumbnail>() {
            @Override
            public void onResponse(Call<OpenloadThumbnail> call, Response<OpenloadThumbnail> response) {
                OpenloadThumbnail open = response.body();
                openload_thumbnail_url = open.getResult();
            }
            @Override
            public void onFailure(Call<OpenloadThumbnail> call, Throwable t) {
                // Log error here since request failed
                Log.e("openloadResult", t.toString());
            }
        });
    }
    private void AlertDialog(String url) {
        dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.alertdialog);
        dialog.setTitle("Test Capcha");
        ImageView captcha = dialog.findViewById(R.id.captcha);
        final EditText captcha_edit_text= dialog.findViewById(R.id.captcha_edit_text);
        captcha_edit_text.requestFocus();
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(mContext.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);
        Glide.with(mContext)
                .load(url).into(captcha);

        Button okButton =  dialog.findViewById(R.id.OKButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                String captcha_text = captcha_edit_text.getText().toString();
                String full_url = "https://api.openload.co/1/file/dl?file=" + file_id + "&ticket=" + ticket + "&captcha_response=" + captcha_text;
                retrofit_2(full_url);
                dialog.dismiss();
            }
        });

        //dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        dialog.show();
    }

    private void retrofit_2(String full_url) {

        ApiInterface apiService =
                ApiClient.getClient(mContext,BASE_URL).create(ApiInterface.class);
        Call<OpenloadResult> call_slider = apiService.getOpenload(full_url);
        call_slider.enqueue(new Callback<OpenloadResult>() {
            @Override
            public void onResponse(Call<OpenloadResult> call, Response<OpenloadResult> response) {
                OpenloadResult open = response.body();
                if (open.getOpenload() != null) {
                    watchActivity(open.getOpenload().getUrl());
                    if(dialog != null)
                        dialog.dismiss();
                } else {
                    found_captcha = true;
                    retrofit_1();
                }
            }
            @Override
            public void onFailure(Call<OpenloadResult> call, Throwable t) {
                Log.e("openloadResult", t.toString());
            }
        });

    }


    private void watchActivity(String url ) {
        String intArray[] = {url,title,openload_thumbnail_url};
        Intent inent = new Intent(mContext, WatchActivity.class);
        inent.putExtra("url", intArray);
        mContext.startActivity(inent);
    }

}
