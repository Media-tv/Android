package com.vacuum.app.plex.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import android.widget.Spinner;

import com.vacuum.app.plex.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Home on 3/10/2018.
 */

public class SettingFragment extends Fragment {
    Context mContext;
    public static final String TAG_SETTING_FRAGMENT = "TAG_SETTING_FRAGMENT";
    Spinner spinner_quality;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View view = inflater.inflate(R.layout.fragment_settings, container, false);
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        mContext = this.getContext();
        spinner_quality = view.findViewById(R.id.spinner_quality);


        setspinner();
        return view;

    }

    private void setspinner() {
        List<String> quality = new ArrayList<>();
        quality.add("Low");
        quality.add("Medium");
        quality.add("High");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.spinner_item_layout_dark, quality);
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
    }


}