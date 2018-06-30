package com.vacuum.app.plex.Splash;

/**
 * Created by Home on 10/14/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.vacuum.app.plex.MainActivity;
import com.vacuum.app.plex.R;
import com.vacuum.app.plex.Utility.ApiClient;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.vacuum.app.plex.Splash.HomeSplashFragment.HOME_SPLASH_FRAGMENT_TAG;
import static com.vacuum.app.plex.Splash.LoginFragment.LOGIN_FRAGMENT_TAG;


public class SplashScreen extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    Context mContext;
    public  SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            /*Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);*/
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

        }

        setContentView(R.layout.activity_splash);

        mContext = this.getApplicationContext();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/airbnb.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        remmber_me();
        home_splash_fragment();
    }

    private void remmber_me() {
        prefs = mContext.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String remmber = prefs.getString("email",null);
        if (remmber != null){
            Intent i = new Intent(mContext, MainActivity.class);
            startActivity(i);
            finish();
        }else {
            home_splash_fragment();
        }
    }


    private void home_splash_fragment() {
        Fragment fragment = new HomeSplashFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment, HOME_SPLASH_FRAGMENT_TAG);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}