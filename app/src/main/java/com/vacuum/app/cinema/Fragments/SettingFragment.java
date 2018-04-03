package com.vacuum.app.cinema.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.vacuum.app.cinema.R;

/**
 * Created by Home on 3/10/2018.
 */

public class SettingFragment extends Fragment {
    Context mContext;
    public  static final String TAG_SETTING_FRAGMENT = "TAG_SETTING_FRAGMENT";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        mContext = this.getContext();

        return view;
    }

}

