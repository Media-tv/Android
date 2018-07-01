package com.vacuum.app.plex.Fragments.MainFragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.vacuum.app.plex.Fragments.EditProfile_Fragment;
import com.vacuum.app.plex.Fragments.SettingFragment;
import com.vacuum.app.plex.MainActivity;
import com.vacuum.app.plex.R;
import com.vacuum.app.plex.Splash.SplashScreen;
import com.vacuum.app.plex.Utility.SingleShotLocationProvider;

import java.util.List;

import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static com.vacuum.app.plex.Fragments.EditProfile_Fragment.EDITPORFILE_FRAGMENT_TAG;
import static com.vacuum.app.plex.Fragments.SettingFragment.TAG_SETTING_FRAGMENT;
import static com.vacuum.app.plex.Splash.SplashScreen.MY_PREFS_NAME;

/**
 * Created by Home on 2/19/2018.
 */

public class ProfileFragment extends Fragment implements View.OnClickListener, RewardedVideoAdListener {
    LinearLayout layout_settings,layout_logout,layout1_editprofile,layout2_payment,layout_close_account;
    Button more_points;
    Context mContext;
    TextView points;
    RewardedVideoAd mRewardedVideoAd;
    SharedPreferences prefs;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        layout_settings = view.findViewById(R.id.layout_settings);
        layout_logout = view.findViewById(R.id.layout_logout);
        layout1_editprofile = view.findViewById(R.id.layout1_editprofile);
        layout2_payment = view.findViewById(R.id.layout2_payment);
        layout_close_account = view.findViewById(R.id.layout_close_account);

        more_points = view.findViewById(R.id.more_points);
        points = view.findViewById(R.id.points);

        mContext = this.getActivity();

        layout_settings.setOnClickListener(this);
        more_points.setOnClickListener(this);
        layout_logout.setOnClickListener(this);
        layout1_editprofile.setOnClickListener(this);
        layout2_payment.setOnClickListener(this);
        layout_close_account.setOnClickListener(this);


        MobileAds.initialize(mContext, "ca-app-pub-3341550634619945~1422870532");

        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(mContext);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();
        prefs = mContext.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        int points_value = prefs.getInt("points",0);
        if (points_value < 3000)
            {
                points.setText(String.valueOf(points_value) + " points");
                points.setTextColor(Color.RED);
            }else
            {
                points.setText(String.valueOf(points_value) + " points");
            }
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.layout_settings:
                switchfragment(new SettingFragment(),TAG_SETTING_FRAGMENT);
                break;
            case R.id.more_points:

                if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                } else {
                    Toast.makeText(mContext, "not loaded yet!", Toast.LENGTH_SHORT).show();
                }
                break;
                case R.id.layout_logout:
                    SharedPreferences preferences = mContext.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                    preferences.edit().remove("email").commit();
                    startActivity(new Intent(mContext, SplashScreen.class));
                    break;
            case R.id.layout1_editprofile:
                //switchfragment(new EditProfile_Fragment(),EDITPORFILE_FRAGMENT_TAG);
                //break;
            case R.id.layout2_payment:
            case R.id.layout_close_account:
                Toast.makeText(mContext, "Go Premium!", Toast.LENGTH_SHORT).show();
                break;

            default:
        }
    }

    private void switchfragment(Fragment fragment,String TAG) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment, TAG);
        fragmentTransaction.addToBackStack(MainActivity.CURRENT_TAG);
        fragmentTransaction.commit();
    }


    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-3341550634619945/4895005821"/*"ca-app-pub-3341550634619945/4093367533"*/,
                new AdRequest.Builder().build());
    }


    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        loadRewardedVideoAd();
    }



    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }
}