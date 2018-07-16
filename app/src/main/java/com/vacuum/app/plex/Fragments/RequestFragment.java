package com.vacuum.app.plex.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.vacuum.app.plex.R;
import com.vacuum.app.plex.Utility.ApiClient;
import com.vacuum.app.plex.Utility.ApiInterface;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.vacuum.app.plex.Splash.SplashScreen.MY_PREFS_NAME;

public class RequestFragment extends Fragment {
    Context mContext;
    public  static final String TAG_REQUEST_FRAGMENT = "TAG_REQUEST_FRAGMENT";
    FirebaseAnalytics mFirebaseAnalytics;
    EditText Request_title,Request_description;
    Button Request_btn;
    SharedPreferences prefs;
    String user;
    TextView request_done;
    LottieAnimationView animation_view_request;
    ApiInterface apiService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.request_fragment, container, false);

        mContext = this.getContext();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(mContext);
        mFirebaseAnalytics.setCurrentScreen(getActivity(), TAG_REQUEST_FRAGMENT, null );
        prefs = mContext.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        user = prefs.getString("id","");
        Request_title = view.findViewById(R.id.Request_title);
        request_done = view.findViewById(R.id.request_done);
        Request_description = view.findViewById(R.id.Request_description);
        Request_btn = view.findViewById(R.id.Request_btn);
        animation_view_request = view.findViewById(R.id.animation_view_request);


        Request_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request();
            }
        });
        return view;
    }
    private void request() {
        String ROOT_URL = "https://mohamedebrahim.000webhostapp.com/";
        apiService =
                ApiClient.getClient(mContext,ROOT_URL).create(ApiInterface.class);

        apiService.requestMovie(user,
                Request_title.getText().toString(),
                Request_description.getText().toString()).enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String responsse ;
                try {
                    responsse  = response.body().string();
                    animation_view_request.setAnimation(R.raw.success);
                    animation_view_request.playAnimation();
                    request_done.setText("RequestMovie Done");
                    request_done.setTextColor(Color.WHITE);
                    Log.e("TAG", "RequestMovie "+responsse);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TAG", "Unable to submit post to API.");
                request_done.setText("Error");
                request_done.setTextColor(Color.RED);
                Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
