package com.vacuum.app.plex.Splash;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.vacuum.app.plex.Adapter.SlidingTextAdapter;
import com.vacuum.app.plex.MainActivity;
import com.vacuum.app.plex.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import me.relex.circleindicator.CircleIndicator;


public class HomeSplashFragment extends Fragment implements View.OnClickListener {
    final static String HOME_SPLASH_FRAGMENT_TAG = "HOME_SPLASH_FRAGMENT_TAG";
    Context mContext;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final String[] texts= {"Sign up for free movies and tv series on your phone","Save your movies or artist to your collection","Share movies to your friends and family for more entertainment"};
    private ArrayList<String> TextArray = new ArrayList<>();
    CircleIndicator indicator;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_splash_screen, container, false);


        mContext = this.getActivity();
        Button login_fragment =  view.findViewById(R.id.login_fragment);
        Button signup_fragment =  view.findViewById(R.id.signup_fragment);
        VideoView videoview =  view.findViewById(R.id.videoview);
        mPager =  view.findViewById(R.id.pager);
        indicator =  view.findViewById(R.id.indicator);
        ImageView background_image_home_fragment = (ImageView) view.findViewById(R.id.background_image_home_fragment);

        login_fragment.setOnClickListener(this);
        signup_fragment.setOnClickListener(this);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            videoview.setVisibility(View.GONE);
            background_image_home_fragment.setVisibility(View.VISIBLE);
        }else {
            Uri uri = Uri.parse("android.resource://" + mContext.getPackageName() + "/" + R.raw.story);
            videoview.setVideoURI(uri);
            videoview.start();
            final int duration = 4000;
            final int colorFrom = Color.parseColor("#10000000");
            final int colorTo = Color.parseColor("#b8000000");
            ColorDrawable[] color = {new ColorDrawable(colorFrom), new ColorDrawable(colorTo)};
            TransitionDrawable transition = new TransitionDrawable(color);
            videoview.setBackground(transition);
            transition.startTransition(duration);
            //Video Loop
            videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
                    mp.setLooping(true);
                    mp.setVolume(0f, 0f);
                }
            });
        }
        init();
        return view;
    }
    private void fragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment, HOME_SPLASH_FRAGMENT_TAG);
        fragmentTransaction.commitAllowingStateLoss();
        fragmentTransaction.addToBackStack(HOME_SPLASH_FRAGMENT_TAG);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.login_fragment:
                fragment(new LoginFragment());
                break;
            case R.id.signup_fragment:
                fragment(new SignupFragment());
                break;
            default:
                break;
        }
    }



    private void init() {
        for(int i=0;i<texts.length;i++)
            TextArray.add(texts[i]);


        mPager.setAdapter(new SlidingTextAdapter(mContext,TextArray));
        indicator.setViewPager(mPager);
        //final float density = getResources().getDisplayMetrics().density;
        //indicator.setRadius(5 * density);
        NUM_PAGES =texts.length;

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {   currentPage = position;        }
            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {            }
            @Override
            public void onPageScrollStateChanged(int pos) {            }
        });

    }
}
