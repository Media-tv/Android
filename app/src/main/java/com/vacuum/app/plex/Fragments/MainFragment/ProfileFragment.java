package com.vacuum.app.plex.Fragments.MainFragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
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
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.vacuum.app.plex.Fragments.SettingFragment;
import com.vacuum.app.plex.MainActivity;
import com.vacuum.app.plex.R;
import com.vacuum.app.plex.Splash.SplashScreen;
import com.vacuum.app.plex.Utility.SingleShotLocationProvider;

import java.util.List;

import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static com.vacuum.app.plex.Splash.SplashScreen.MY_PREFS_NAME;

/**
 * Created by Home on 2/19/2018.
 */

public class ProfileFragment extends Fragment implements View.OnClickListener, RewardedVideoAdListener {
    LinearLayout layout_settings,layout_logout;
    Button more_points;
    Context mContext;
    RewardedVideoAd mRewardedVideoAd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        layout_settings = view.findViewById(R.id.layout_settings);
        layout_logout = view.findViewById(R.id.layout_logout);

        more_points = view.findViewById(R.id.more_points);

        mContext = this.getActivity();

        layout_settings.setOnClickListener(this);
        more_points.setOnClickListener(this);
        layout_logout.setOnClickListener(this);


        MobileAds.initialize(mContext, "ca-app-pub-3341550634619945~1422870532");

        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(mContext);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.layout_settings:
                Fragment fragment = new SettingFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment, SettingFragment.TAG_SETTING_FRAGMENT);
                fragmentTransaction.addToBackStack(MainActivity.CURRENT_TAG);
                fragmentTransaction.commit();
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
            default:
        }
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