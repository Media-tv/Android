package com.vacuum.app.plex.Splash;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
    TextView terms,terms2;
    Button login_btn;
    Context mContext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        terms =  view.findViewById(R.id.terms);
        terms2 =  view.findViewById(R.id.terms2);
        login_btn =  view.findViewById(R.id.login_btn);

        login_email =  view.findViewById(R.id.login_email);
        login_password =  view.findViewById(R.id.login_password);


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
        terms2.setOnClickListener(this);



        return view;
    }


    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.login_btn:
                login();
                break;

            case R.id.terms2:
                //startActivity(new Intent(getActivity(), PrivacyPolicyActivity.class));
                //getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
        }
    }

    private void login() {
        String ROOT_URL = "https://mohamedebrahim.000webhostapp.com/";

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiInterface api = retrofit.create(ApiInterface.class);
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
                Toast.makeText(mContext,"unable to login", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void skipSplash()
    {
        Intent i = new Intent(getActivity(), MainActivity.class);
        startActivity(i);
        getActivity().finish();
    }





}