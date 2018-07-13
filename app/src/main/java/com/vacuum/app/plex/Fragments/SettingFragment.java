package com.vacuum.app.plex.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vacuum.app.plex.MainActivity;
import com.vacuum.app.plex.R;
import com.vacuum.app.plex.Splash.SplashScreen;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.vacuum.app.plex.Splash.SplashScreen.MY_PREFS_NAME;

/**
 * Created by Home on 3/10/2018.
 */

public class SettingFragment extends Fragment implements View.OnClickListener{
    static Context mContext;
    TextView board,brand,sdk,code,display,model,logout_name,version;
    public static final String TAG_SETTING_FRAGMENT = "TAG_SETTING_FRAGMENT";
    Spinner spinner_quality,spinner_quality_download;
    SharedPreferences prefs;
    LinearLayout layout_logout,layout_support,layout_third_party_libs,layout_privacy_policy;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View view = inflater.inflate(R.layout.fragment_settings, container, false);
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        mContext = this.getContext();
        spinner_quality = view.findViewById(R.id.spinner_quality);
        spinner_quality_download = view.findViewById(R.id.spinner_quality_download);

        //textviews
        board = view.findViewById(R.id.board);
        brand = view.findViewById(R.id.brand);
        sdk = view.findViewById(R.id.sdk);
        code = view.findViewById(R.id.code);
        display = view.findViewById(R.id.display);
        model = view.findViewById(R.id.model);
        version = view.findViewById(R.id.version);
        logout_name = view.findViewById(R.id.logout_name);
        layout_logout = view.findViewById(R.id.layout_logout);
        layout_support = view.findViewById(R.id.support);
        layout_third_party_libs = view.findViewById(R.id.layout_third_party_libs);
        layout_privacy_policy = view.findViewById(R.id.layout_privacy_policy);


        layout_logout.setOnClickListener(this);
        layout_support.setOnClickListener(this);
        layout_third_party_libs.setOnClickListener(this);
        layout_privacy_policy.setOnClickListener(this);

        prefs = mContext.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        setspinner();
        getDetailsMANUFACTURER();

        return view;
    }

    private void getDetailsMANUFACTURER() {
        PackageInfo pInfo = null;
        String versionName = null;
        int versionCode = 0;
        try {
            pInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            versionName = pInfo.versionName;
            versionCode = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        //==============================
        Field[] fields = Build.VERSION_CODES.class.getFields();
        String osName = fields[Build.VERSION.SDK_INT + 1].getName();
        ////===============================
        version.setText(versionName+"(Code 2854-"+versionCode+")"+" armV7");
        brand.setText("Brand: "+Build.BRAND);
        model.setText("MODEL: "+Build.MODEL);
        sdk.setText("SDK: "+Build.VERSION.SDK+" OS:"+osName);
        code.setText("Code: "+Build.VERSION.RELEASE);
        board.setText("Board: "+Build.BOARD);
        display.setText("Display: "+Build.DISPLAY);
        logout_name.setText("You are logged in as "+(prefs.getString("full_name","")));
    }
    private void setspinner() {
        List<String> quality_stream = new ArrayList<>();
        quality_stream.add("Automatic");
        quality_stream.add("Low");
        quality_stream.add("Normal");
        quality_stream.add("High");
        quality_stream.add("Very High");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.spinner_item_layout_dark, quality_stream);
        spinner_quality.setAdapter(adapter);
        spinner_quality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });


//===================================
        List<String> quality_down = new ArrayList<>();
        quality_down.add("Normal");
        quality_down.add("High");
        quality_down.add("Very High");

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(mContext, R.layout.spinner_item_layout_dark, quality_down);
        spinner_quality_download.setAdapter(adapter2);
        spinner_quality_download.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_logout:

                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(mContext, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(mContext);
                }
                builder.setTitle("Sign Out")
                        .setMessage("Are you sure you want to sign out of plex account on all devices?")
                        .setPositiveButton("Sign Out", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                SharedPreferences settings = mContext.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                                settings.edit().clear().commit();
                                startActivity(new Intent(mContext, SplashScreen.class));
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                break;
                case R.id.support:
                    Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                    String facebookUrl = getFacebookPageURL();
                    facebookIntent.setData(Uri.parse(facebookUrl));
                    startActivity(facebookIntent);
                    break;
            case R.id.layout_third_party_libs:
                setup_webview("https://mohamedebrahim.000webhostapp.com/plex/WEB/open%20source%20website/third-party-software.html");
                break;
            case R.id.layout_privacy_policy:
                setup_webview("https://mohamedebrahim.000webhostapp.com/plex/WEB/open%20source%20website/Open%20Source%20Software%20Center.html");
                break;

        }

    }

    private void setup_webview(String url) {
        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
        alert.setTitle("");

        WebView wv = new WebView(mContext);
        wv.loadUrl(url);
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);

                return true;
            }
        });

        alert.setView(wv);
        alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    public String getFacebookPageURL() {
          String FACEBOOK_URL = "https://www.facebook.com/Plex.media.company";
          String FACEBOOK_PAGE_ID = "Plex.media.company";
        PackageManager packageManager = mContext.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }

    private void switchfragment(Fragment fragment,String TAG) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment, TAG);
        fragmentTransaction.addToBackStack(MainActivity.CURRENT_TAG);
        fragmentTransaction.commit();
    }


}