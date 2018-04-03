package com.vacuum.app.cinema.Fragments.MainFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.vacuum.app.cinema.Fragments.SettingFragment;
import com.vacuum.app.cinema.MainActivity;
import com.vacuum.app.cinema.R;

/**
 * Created by Home on 2/19/2018.
 */

public class ProfileFragment extends Fragment implements View.OnClickListener{
    LinearLayout layout_settings;
    Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        layout_settings = view.findViewById(R.id.layout_settings);

        mContext = this.getContext();

        layout_settings.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.layout_settings:
                Fragment fragment = new SettingFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, SettingFragment.TAG_SETTING_FRAGMENT);
                fragmentTransaction.addToBackStack(MainActivity.CURRENT_TAG);
                fragmentTransaction.commit();
                break;
            default:
        }


    }






}