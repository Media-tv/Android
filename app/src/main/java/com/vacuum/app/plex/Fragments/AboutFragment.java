package com.vacuum.app.plex.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.vacuum.app.plex.R;

import java.lang.reflect.Field;


/**
 * Created by Home on 10/19/2017.
 */

public class AboutFragment extends Fragment {
    Context mContext;
    public  static final String TAG_ABOUT_FRAGMENT = "TAG_ABOUT_FRAGMENT";
    FirebaseAnalytics mFirebaseAnalytics;

    TextView version_1;
    LinearLayout layout_facebook,layout_twitter,layout_telegram,layout_contactus;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.about_fragment, container, false);

        mContext = this.getContext();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(mContext);
        mFirebaseAnalytics.setCurrentScreen(getActivity(), TAG_ABOUT_FRAGMENT, null );


         version_1 = view.findViewById(R.id.version_1);
         layout_facebook = view.findViewById(R.id.layout_facebook);
         layout_twitter = view.findViewById(R.id.layout_twitter);
         layout_telegram = view.findViewById(R.id.layout_telegram);
        layout_contactus = view.findViewById(R.id.layout_contactus);


        setup();
        return view;
    }

    private void setup() {
        layout_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.facebook.com/Plex.media.company");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        layout_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://twitter.com/mohamedhima96");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        layout_telegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/joinchat/FyEAvQw6fjkaATfUmPWo-A"));
                startActivity(intent);
            }
        });
        layout_contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.ebrahimm131@gmail.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
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
        version_1.setText(versionName+"(Code 2854-"+versionCode+")"+" armV7");

    }

}
