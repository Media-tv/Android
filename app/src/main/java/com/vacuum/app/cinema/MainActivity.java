package com.vacuum.app.cinema;


import android.annotation.SuppressLint;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.vacuum.app.cinema.Fragments.HomeFragment;
import com.vacuum.app.cinema.Fragments.MoviesFragment;
import com.vacuum.app.cinema.Fragments.NotificationsFragment;
import com.vacuum.app.cinema.Fragments.PhotosFragment;
import com.vacuum.app.cinema.Fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    VideoView videoView;
    MediaController mediaController;
    FloatingActionButton fab;


    private String[] activityTitles;
    public static int navItemIndex = 0;
    private static final String TAG_HOME = "home";
    private static final String TAG_PHOTOS = "photos";
    private static final String TAG_MOVIES = "movies";
    private static final String TAG_NOTIFICATIONS = "notifications";
    private static final String TAG_SETTINGS = "settings";
    public static String CURRENT_TAG = TAG_HOME;
    //private Toolbar toolbar;

    // urls to load navigation header background image
    // and profile image
    private static final String urlNavHeaderBg = "https://api.androidhive.info/images/nav-menu-header-bg.jpg";
    private static final String urlProfileImg = "https://avatars2.githubusercontent.com/u/16405013?s=460&v=4";
    String url_Video = "https://s26.vidbom.com/6jmnqa4qsqazsalriusadzpva7ohybl2qrtz3yhymn76biocmrfbtm2y4xxa/v.mp4";
    ImageView btn_one,btn_two,btn_three,btn_four;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab);
        btn_one =  findViewById(R.id.btn_one);
        btn_two =  findViewById(R.id.btn_two);
        btn_three =  findViewById(R.id.btn_three);
        btn_four =  findViewById(R.id.btn_four);


        fab.setOnClickListener(this);
        btn_one.setOnClickListener(this);
        btn_two.setOnClickListener(this);
        btn_three.setOnClickListener(this);
        btn_four.setOnClickListener(this);


        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);



        /*videoView = (VideoView)findViewById(R.id.VideoView);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        Uri uri=Uri.parse(url);
        videoView.setVideoURI(uri);
        videoView.start();*/
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }*/

        loadHomeFragment();
    }

    private void loadHomeFragment() {

        //setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {

            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app

                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();

    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                // photos
                PhotosFragment photosFragment = new PhotosFragment();
                return photosFragment;
            case 2:
                // movies fragment
                MoviesFragment moviesFragment = new MoviesFragment();
                return moviesFragment;
            case 3:
                // notifications fragment
                NotificationsFragment notificationsFragment = new NotificationsFragment();
                return notificationsFragment;

            case 4:
                // settings fragment
                SettingsFragment settingsFragment = new SettingsFragment();
                return settingsFragment;
            default:
                return new HomeFragment();
        }
    }
    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            //Replacing the main content with ContentFragment Which is our Inbox View;

            case R.id.fab:
                Toast.makeText(this, "Fab", Toast.LENGTH_SHORT).show();
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                break;
            case R.id.btn_one:
                navItemIndex = 1;
                CURRENT_TAG = TAG_HOME;
                break;
            case R.id.btn_two:
                navItemIndex = 2;
                CURRENT_TAG = TAG_PHOTOS;
                break;
            case R.id.btn_three:
                navItemIndex = 3;
                CURRENT_TAG = TAG_MOVIES;
                break;
            case R.id.btn_four:
                navItemIndex = 4;
                CURRENT_TAG = TAG_NOTIFICATIONS;
                break;
            /*case R.id.btn_one:
                navItemIndex = 4;
                CURRENT_TAG = TAG_SETTINGS;
                break;
            case R.id.btn_three:
                // launch new intent instead of loading fragment
                startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                drawer.closeDrawers();
                return true;
            case R.id.nav_privacy_policy:
                // launch new intent instead of loading fragment
                startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
                drawer.closeDrawers();
                return true;*/
            default:
                navItemIndex = 0;
        }

        loadHomeFragment();
    }



}

