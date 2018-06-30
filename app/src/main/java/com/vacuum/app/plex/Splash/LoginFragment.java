package com.vacuum.app.plex.Splash;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vacuum.app.plex.MainActivity;
import com.vacuum.app.plex.Model.User;
import com.vacuum.app.plex.R;
import com.vacuum.app.plex.Utility.ApiClient;
import com.vacuum.app.plex.Utility.ApiInterface;

import java.io.IOException;
import java.net.HttpURLConnection;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static com.vacuum.app.plex.Splash.SignupFragment.SIGNUP_FRAGMENT_TAG;
import static com.vacuum.app.plex.Splash.SplashScreen.MY_PREFS_NAME;

public class LoginFragment extends Fragment implements View.OnClickListener {

    final static String LOGIN_FRAGMENT_TAG ="LOGIN_FRAGMENT_TAG";

    private EditText login_email,login_password;
    TextView terms,error_message;
    Button login_btn;
    RelativeLayout background_layout;
    Context mContext;
    AnimationDrawable anim;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        terms =  view.findViewById(R.id.terms);
        error_message =  view.findViewById(R.id.error_message);

        login_btn =  view.findViewById(R.id.login_btn);

        login_email =  view.findViewById(R.id.login_email);
        login_password =  view.findViewById(R.id.login_password);
        background_layout =  view.findViewById(R.id.background_layout);

        mContext = this.getActivity();

        login_password.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    login();
                    return true;
                }
                return false;
            }
        });




        login_btn.setOnClickListener(this);


        anim = (AnimationDrawable) background_layout.getBackground();
        anim.setEnterFadeDuration(4000);
        anim.setExitFadeDuration(4000);


        check_edittext_fileds();
        return view;
    }




    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.login_btn:
                validateFields();
                break;
        }
    }

    private void login() {
        String ROOT_URL = "https://mohamedebrahim.000webhostapp.com/";


        //===============================================================================
        ApiInterface api = ApiClient.getClient(mContext,ROOT_URL).create(ApiInterface.class);
        //==================================================================================
        api.loging_user(
                login_email.getText().toString(),
                login_password.getText().toString()
        ).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.isSuccessful()) {
                    User u = response.body();
                        Toast.makeText(mContext,"Hello, "+u.getFullName(), Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = mContext.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                        editor.putString("email",u.getEmail());
                        editor.putString("password",u.getPassword());
                        editor.putString("phone",u.getPhone());
                        editor.putString("address",u.getAddress());
                        editor.putInt("points",u.getPoints());
                        editor.apply();
                        skipSplash();

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                error_message.setVisibility(View.VISIBLE);
                login_btn.setText("LOG IN");

                Log.e("TAG",t.toString());
            }
        });
    }
    private void skipSplash()
    {
        Intent i = new Intent(getActivity(), MainActivity.class);
        startActivity(i);
        getActivity().finish();
    }

    private void validateFields() {
        if (login_email.getText().length() == 0) {
            login_email.setError("Empty Field");
        }else if (login_password.getText().length() == 0) {
            login_password.setError("Empty Field");
        }else {
            login_btn.setText("logging in...");
            error_message.setVisibility(View.GONE);
            login();
        }
    }
    private void check_edittext_fileds() {
        login_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String ed_text = login_email.getText().toString().trim();
                if(ed_text.isEmpty() || ed_text.length() == 0 || ed_text.equals("") || ed_text == null)
                {
                    //EditText is empty
                    login_btn.setAlpha(0.5f);
                    login_btn.setTextColor(Color.GRAY);
                }else {
                    login_btn.setAlpha(1);
                    login_btn.setTextColor(Color.BLACK);
                }

            }
        });
        login_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String ed_text = login_password.getText().toString().trim();
                if(ed_text.isEmpty() || ed_text.length() == 0 || ed_text.equals("") || ed_text == null)
                {
                    //EditText is empty
                    login_btn.setAlpha(0.5f);
                    login_btn.setTextColor(Color.GRAY);
                }else {
                    login_btn.setAlpha(1);
                    login_btn.setTextColor(Color.BLACK);
                }

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (anim != null && !anim.isRunning())
            anim.start();
    }

//Stopping animation:- stop the animation on onPause.
    @Override
    public void onPause() {
        super.onPause();
        if (anim != null && anim.isRunning())
            anim.stop();
    }



}