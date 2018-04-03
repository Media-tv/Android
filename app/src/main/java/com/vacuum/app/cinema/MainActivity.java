package com.vacuum.app.cinema;


import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.vacuum.app.cinema.Fragments.MainFragment.HomeFragment;
import com.vacuum.app.cinema.Fragments.MainFragment.TvShowsFragment;
import com.vacuum.app.cinema.Fragments.MainFragment.DiscoverFragment;
import com.vacuum.app.cinema.Fragments.MainFragment.SearchFragment;
import com.vacuum.app.cinema.Fragments.MainFragment.ProfileFragment;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    FloatingActionButton btn_three;
    public static int navItemIndex = 3;
    private static final String TAG_HOME = "TAG_HOME";
    private static final String TAG_TV = "TAG_TV";
    private static final String TAG_DISCOVER = "TAG_DISCOVER";
    private static final String TAG_PROFILE = "TAG_PROFILE";
    private static final String TAG_SEARCH = "TAG_SEARCH";
    public static String CURRENT_TAG = TAG_HOME;
    ImageView btn_one,btn_two,btn_four,btn_five;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/brownregular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        btn_one =  findViewById(R.id.btn_one);
        btn_two =  findViewById(R.id.btn_two);
        btn_three = findViewById(R.id.btn_three);
        btn_four =  findViewById(R.id.btn_four);
        btn_five =  findViewById(R.id.btn_five);


        btn_one.setOnClickListener(this);
        btn_two.setOnClickListener(this);
        btn_three.setOnClickListener(this);
        btn_four.setOnClickListener(this);
        btn_five.setOnClickListener(this);

        loadHomeFragment();
    }

    private void loadHomeFragment() {
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.addToBackStack(TAG_HOME);
                fragmentTransaction.commit();

    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 1:
                TvShowsFragment tvShowsFragment = new TvShowsFragment();
                return tvShowsFragment;
            case 2:
                DiscoverFragment discoverFragment = new DiscoverFragment();
                return discoverFragment;
            case 3:
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;

            case 4:
                SearchFragment searchFragment = new SearchFragment();
                 return searchFragment;
            case 5:
                ProfileFragment profileFragment = new ProfileFragment();
                return profileFragment;
            default:
                return new HomeFragment();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.btn_one:
                navItemIndex = 1;
                CURRENT_TAG = TAG_TV;
                break;
            case R.id.btn_two:
                navItemIndex = 2;
                CURRENT_TAG = TAG_DISCOVER;
                break;
            case R.id.btn_three:
                navItemIndex =3;
                CURRENT_TAG = TAG_HOME;
                break;
            case R.id.btn_four:
                navItemIndex = 4;
                CURRENT_TAG = TAG_SEARCH;
                break;
            case R.id.btn_five:
                navItemIndex = 5;
                CURRENT_TAG = TAG_PROFILE;
                break;
            default:
                navItemIndex = 3;
        }

        loadHomeFragment();
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}

