package com.vacuum.app.plex.Fragments.MainFragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

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
import com.vacuum.app.plex.Fragments.AboutFragment;
import com.vacuum.app.plex.Fragments.EditProfile_Fragment;
import com.vacuum.app.plex.Fragments.SettingFragment;
import com.vacuum.app.plex.MainActivity;
import com.vacuum.app.plex.R;
import com.vacuum.app.plex.Splash.SplashScreen;
import com.vacuum.app.plex.Utility.CollapsingImageLayout;
import com.vacuum.app.plex.Utility.SingleShotLocationProvider;

import java.util.List;

import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static com.vacuum.app.plex.Fragments.AboutFragment.TAG_ABOUT_FRAGMENT;
import static com.vacuum.app.plex.Fragments.EditProfile_Fragment.EDITPORFILE_FRAGMENT_TAG;
import static com.vacuum.app.plex.Fragments.SettingFragment.TAG_SETTING_FRAGMENT;
import static com.vacuum.app.plex.Splash.SplashScreen.MY_PREFS_NAME;

/**
 * Created by Home on 2/19/2018.
 */

public class ProfileFragment extends Fragment implements View.OnClickListener, RewardedVideoAdListener {
    LinearLayout layout_settings,layout2_payment,layout_about,layout_share;
    Button more_points;
    Context mContext;
    TextView points;
    RewardedVideoAd mRewardedVideoAd;
    SharedPreferences prefs;
    String ADMOB_PLEX_REWARDED_1;

    CollapsingImageLayout layout1_editprofile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        layout_settings = view.findViewById(R.id.layout_settings);
        layout1_editprofile = view.findViewById(R.id.layout1_editprofile);
        layout2_payment = view.findViewById(R.id.layout2_payment);
        layout_about = view.findViewById(R.id.layout_about);
        layout_share = view.findViewById(R.id.layout_share);

        more_points = view.findViewById(R.id.more_points);
        points = view.findViewById(R.id.points);

        mContext = this.getActivity();



        prefs = mContext.getSharedPreferences("Plex", Activity.MODE_PRIVATE);
        ADMOB_PLEX_REWARDED_1 = prefs.getString("ADMOB_PLEX_REWARDED_1",null);


        layout_settings.setOnClickListener(this);
        more_points.setOnClickListener(this);
        layout1_editprofile.setOnClickListener(this);
        layout2_payment.setOnClickListener(this);
        layout_about.setOnClickListener(this);
        layout_share.setOnClickListener(this);


        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(mContext);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();
        //prefs = mContext.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
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
                }else {
                    Toast.makeText(mContext, "Not loaded yet!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.layout1_editprofile:
                switchfragment(new EditProfile_Fragment(),EDITPORFILE_FRAGMENT_TAG);
                break;
            case R.id.layout2_payment:
                Toast.makeText(mContext, "Go Premium!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.layout_about:
                switchfragment(new AboutFragment(),TAG_ABOUT_FRAGMENT);
                break;
            case R.id.layout_share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "If you guys want a free (Movie/TVseries) app, Give Plex a try. https://github.com/mohamedebrahim96/PlexMedia/raw/master/version/release/app-release.apk";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "If you guys want a free (Movie/TVseries) app, Give Plex a try.");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
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
        mRewardedVideoAd.loadAd(ADMOB_PLEX_REWARDED_1,//ca-app-pub-3940256099942544/5224354917
                new AdRequest.Builder().build());
    }
    @Override
    public void onRewardedVideoAdLoaded() {
       // mRewardedVideoAd.show();
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


    @Override
    public void onResume() {
        mRewardedVideoAd.resume(mContext);
        super.onResume();
    }

    @Override
    public void onPause() {
        mRewardedVideoAd.pause(mContext);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(mContext);
        super.onDestroy();
    }
}