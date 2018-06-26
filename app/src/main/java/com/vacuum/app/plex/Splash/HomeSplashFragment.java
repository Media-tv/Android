package com.vacuum.app.plex.Splash;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.VideoView;

import com.vacuum.app.plex.MainActivity;
import com.vacuum.app.plex.R;

import static com.vacuum.app.plex.Splash.SignupFragment.SIGNUP_FRAGMENT_TAG;

public class HomeSplashFragment extends Fragment implements View.OnClickListener {

    final static String HOME_SPLASH_FRAGMENT_TAG = "HOME_SPLASH_FRAGMENT_TAG";
    Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_splash_screen, container, false);


        mContext = this.getActivity();
        Button login_fragment = (Button) view.findViewById(R.id.login_fragment);
        Button signup_fragment = (Button) view.findViewById(R.id.signup_fragment);
        VideoView videoview = (VideoView) view.findViewById(R.id.videoview);

        login_fragment.setOnClickListener(this);
        signup_fragment.setOnClickListener(this);



        Uri uri = Uri.parse("android.resource://" + mContext.getPackageName() + "/" + R.raw.story);
        videoview.setVideoURI(uri);
        videoview.start();

        //Video Loop
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
                mp.setLooping(true);
                mp.setVolume(0f, 0f);

            }
        });

        final int duration = 4000;
        final int colorFrom = Color.parseColor("#10000000");
        final int colorTo = Color.parseColor("#b8000000");
        ColorDrawable[] color = {new ColorDrawable(colorFrom), new ColorDrawable(colorTo)};
        TransitionDrawable transition = new TransitionDrawable(color);
        videoview.setBackground(transition);
        transition.startTransition(duration);


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

    private void skipSplash()
    {
        Intent i = new Intent(getActivity(), MainActivity.class);
        startActivity(i);
        getActivity().finish();
    }
}
