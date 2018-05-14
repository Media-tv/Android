package com.vacuum.app.cinema;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.PowerManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.JsonObject;
import com.vacuum.app.cinema.Fragments.MainFragment.HomeFragment;
import com.vacuum.app.cinema.Fragments.MainFragment.TvShowsFragment;
import com.vacuum.app.cinema.Fragments.MainFragment.DiscoverFragment;
import com.vacuum.app.cinema.Fragments.MainFragment.SearchFragment;
import com.vacuum.app.cinema.Fragments.MainFragment.ProfileFragment;
import com.vacuum.app.cinema.Model.API_KEY;
import com.vacuum.app.cinema.Model.Update;
import com.vacuum.app.cinema.Utility.ApiClient;
import com.vacuum.app.cinema.Utility.ApiInterface;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
    Context mContext;
    AlertDialog.Builder alertadd;
    private ApiInterface apiService;
    ProgressDialog mProgressDialog;
    String link;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private FirebaseAnalytics mFirebaseAnalytics;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.setStatusBarColor(ContextCompat.getColor(this,R.color.transparent));

        }
        setContentView(R.layout.activity_main);


        MobileAds.initialize(this, "ca-app-pub-3341550634619945~1422870532");
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/brownregular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        prefs = getSharedPreferences("Plex", Activity.MODE_PRIVATE);
        editor = prefs.edit();




        mContext = this.getApplicationContext();
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


        upadate_retrofit();
        get_API_keys();
        analytics();
    }


    private void analytics() {

        final Fabric fabric = new Fabric.Builder(this)
                .kits(new Crashlytics())
                .debuggable(true)           // Enables Crashlytics debugger
                .build();
        Fabric.with(fabric);




        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setCurrentScreen(this, "MainActivity", null );

        /*Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "new changes");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);*/
    }


    private void loadHomeFragment() {
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
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


    private int version_number() {

        PackageInfo pInfo = null;
        int versionCode = 0;
        try {
            pInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            String versionName = pInfo.versionName;
            versionCode = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;

    }

    private void upadate_retrofit() {

        apiService =
                ApiClient.getClient(mContext).create(ApiInterface.class);

        String webhost00free = "https://mohamedebrahim.000webhostapp.com/cimaclub/update.php" ;

        Call<Update> call_UpComing = apiService.getUpdateVersion(webhost00free);
        call_UpComing.enqueue(new Callback<Update>() {
            @Override
            public void onResponse(Call<Update>call, Response<Update> response) {

                try{
                    Update c = response.body();
                    //Log.e("TAG : ",c);
                    String versionname = c.getVersionname();
                    int  versioncode = c.getVersioncode();
                    String message =c.getMessage();
                    String title =c.getTitle();
                    link = c.getDownloadLink();
                    if(version_number() == versioncode){
                        AlertDialog(true,versioncode,title,message);
                    }else {
                        AlertDialog(false,versioncode,title,message);
                    }
                }catch (Exception e){
                    Log.e("TAG : Expetion :: ",e.toString());
                }

            }
            @Override
            public void onFailure(Call<Update>call, Throwable t) {
                // Log error here since request failed
                Log.e("TAG : onFailure", t.toString());
            }
        });
    }

    private void AlertDialog(Boolean x,int versioncode,String title,String message) {

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alertdialog_update);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();
        Button update_btn = dialog.findViewById(R.id.update_btn);
        TextView title_view = dialog.findViewById(R.id.title);
        TextView message_update = dialog.findViewById(R.id.message_update);
        ImageView background_image = dialog.findViewById(R.id.background_image);

        if(!x) {
            title_view.setText(title);
            message_update.setText(message);
            update_btn.setText("UPDATE");
            background_image.setImageResource(R.drawable.update_error);
            update_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                    downlaoding();

                }
            });
        }else {

            update_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                    //Log.e("TAG","Button clicked");
                }
            });
        }
    }

    private void downlaoding() {

// instantiate it within the onCreate method
        mProgressDialog = new ProgressDialog(MainActivity.this);
        mProgressDialog.setMessage("A message");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);

// execute this when the downloader must be fired
        final DownloadTask downloadTask = new DownloadTask(MainActivity.this);
        downloadTask.execute(link);

        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                downloadTask.cancel(true);
            }
        });
    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                output = new FileOutputStream("/sdcard/BoxMovies.v1.8.apk");

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            mProgressDialog.dismiss();
            if (result != null)
                Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show();
        }
    }

    private void get_API_keys() {


        prefs = getSharedPreferences("Plex", Activity.MODE_PRIVATE);
        String TMBDB_API_KEY = prefs.getString("TMBDB_API_KEY",null);

        if(TMBDB_API_KEY != null){
            loadHomeFragment();
        }else {
            apiService =
                    ApiClient.getClient(mContext).create(ApiInterface.class);

            String webhost00free = "https://mohamedebrahim.000webhostapp.com/cimaclub/getAPIkey.php";

            Call<API_KEY> call_UpComing = apiService.getApiKEY(webhost00free);
            call_UpComing.enqueue(new Callback<API_KEY>() {
                @Override
                public void onResponse(Call<API_KEY> call, Response<API_KEY> response) {
                    editor.putString("TMBDB_API_KEY", response.body().getTMBDBAPIKEY());
                    editor.putString("OPENLOAD_API_Login", response.body().getOPENLOADAPILogin());
                    editor.putString("OPENLOAD_API_KEY", response.body().getOPENLOADAPIKEY());
                    editor.commit();
                    loadHomeFragment();
                }

                @Override
                public void onFailure(Call<API_KEY> call, Throwable t) {
                    // Log error here since request failed
                    Log.e("TAG : onFailure", t.toString());
                }
            });

        }
    }









}

